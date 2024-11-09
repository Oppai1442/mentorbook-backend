package com.hsf301.project.service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.hsf301.project.model.wallet.Wallet;
import com.hsf301.project.model.transactionHistory.TransactionHistory;
import com.hsf301.project.model.user.User;
import com.hsf301.project.repository.WalletRepository;
import com.hsf301.project.repository.TransactionHistoryRepository;
import com.hsf301.project.repository.UserRepository;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Value("${VNPay.vnp_TmnCode}")
    private String vnp_TmnCode;

    @Value("${VNPay.vnp_HashSecret}")
    private String vnp_HashSecret;

    @Value("${VNPay.vnp_Url}")
    private String vnp_Url;

    @Value("${VNPay.Debug:false}")
    private boolean vnpDebug;

    // Lấy ví người dùng theo ID
    public Wallet getWalletByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        return walletRepository.findByUser(user);
    }

    // Tạo URL thanh toán và lưu giao dịch ở trạng thái "pending"
    public String createPaymentUrl(Long userId, double amount, String returnUrl) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", vnp_TmnCode);
        params.put("vnp_Amount", String.valueOf((int) (amount * 100)));
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", UUID.randomUUID().toString());
        params.put("vnp_OrderInfo", "Thanh toan don hang user : " + userId);
        params.put("vnp_Locale", "vn");
        params.put("vnp_OrderType", "other");

        String baseConfirmUrl = "http://localhost:8080/api/wallet/confirm-deposit";
        String fullReturnUrl = baseConfirmUrl + "?redirectUrl="
                + URLEncoder.encode(returnUrl, StandardCharsets.UTF_8.toString());
        params.put("vnp_ReturnUrl", fullReturnUrl);
        params.put("vnp_IpAddr", "127.0.0.1");

        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String createDate = sdf.format(new Date());
        Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.MINUTE, 30);
        String expireDateStr = sdf.format(expireDate.getTime());

        params.put("vnp_CreateDate", createDate);
        params.put("vnp_ExpireDate", expireDateStr);

        String queryUrl = buildQueryUrl(params);
        if (vnpDebug) {
            System.out.println("VNPay.Debug - Query URL for signature: " + queryUrl);
        }
        String secureHash = hmacSHA512(vnp_HashSecret, queryUrl);
        
        savePendingTransaction(userId, amount, params.get("vnp_TxnRef"), params.get("vnp_OrderInfo"));

        return vnp_Url + "?" + queryUrl + "&vnp_SecureHash=" + secureHash;
    }

    // Lưu giao dịch với trạng thái "pending"
    private void savePendingTransaction(Long userId, double amount, String txnRef, String orderInfo) {
        User user = userRepository.findByUserId(userId);
        TransactionHistory transaction = new TransactionHistory();
        transaction.setUser(user);
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setPaymentMethod("VNPay");
        transaction.setCreatedDate(LocalDateTime.now());
        transaction.setStatus("pending");
        transactionHistoryRepository.save(transaction);
    }

    // Xây dựng URL cho query
    private static String buildQueryUrl(Map<String, String> params) throws Exception {
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append('=');
                sb.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                sb.append('&');
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    // Tạo chữ ký HMAC SHA512
    private static String hmacSHA512(String key, String data) throws Exception {
        Mac hmacSHA512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSHA512.init(secretKey);
        byte[] hash = hmacSHA512.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Xác minh tính hợp lệ của giao dịch từ VNPay
    public boolean isValidTransaction(Map<String, String> params) {
        String receivedSecureHash = params.remove("vnp_SecureHash");
        params.remove("redirectUrl");

        if (receivedSecureHash == null) {
            if (vnpDebug) {
                System.out.println("VNPay.Debug - Received SecureHash is null");
            }
            return false;
        }

        if (vnpDebug) {
            System.out.println("VNPay.Debug - Parameters for validation:");
            params.forEach((key, value) -> System.out.println(key + ": " + value));
        }

        String generatedSecureHash = generateSecureHash(vnp_HashSecret, params);

        if (vnpDebug) {
            System.out.println("VNPay.Debug - Received SecureHash: " + receivedSecureHash);
            System.out.println("VNPay.Debug - Generated SecureHash: " + generatedSecureHash);
        }

        return receivedSecureHash.equalsIgnoreCase(generatedSecureHash);
    }

    private String generateSecureHash(String key, String data) {
        try {
            return hmacSHA512(key, data);
        } catch (Exception e) {
            throw new RuntimeException("Error generating secure hash", e);
        }
    }    

    private String generateSecureHash(String key, Map<String, String> params) {
        try {
            String queryUrl = buildQueryUrl(params);
            if (vnpDebug) {
                System.out.println("VNPay.Debug - Generated query URL for secure hash: " + queryUrl);
            }
            return hmacSHA512(key, queryUrl);
        } catch (Exception e) {
            throw new RuntimeException("Error generating secure hash", e);
        }
    }

    // Xác nhận giao dịch và cập nhật trạng thái giao dịch
    public void confirmAndAddTransaction(Map<String, String> params) {
        String userIdStr = params.get("vnp_OrderInfo").split(":")[1].trim();
        Long userId = Long.parseLong(userIdStr);
        Double amount = Double.valueOf(params.get("vnp_Amount")) / 100;

        User user = userRepository.findByUserId(userId);
        Wallet wallet = walletRepository.findByUser(user);
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUser(user);
            wallet.setBalance(BigDecimal.ZERO);
            wallet.setFreeze(BigDecimal.ZERO);
            wallet.setLastUpdated(LocalDateTime.now());
        }

        wallet.setBalance(wallet.getBalance().add(BigDecimal.valueOf(amount)));
        wallet.setLastUpdated(LocalDateTime.now());
        walletRepository.save(wallet);

        TransactionHistory transaction = transactionHistoryRepository.findByUserAndAmountAndStatus(user, amount, "pending");
        if (transaction != null) {
            transaction.setCompletionTime(LocalDateTime.now());
            transaction.setStatus("success");
            transactionHistoryRepository.save(transaction);
        }
    }
}
