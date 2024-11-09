package com.hsf301.project.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hsf301.project.service.WalletService;
import com.hsf301.project.model.request.WalletDepositRequest;
import com.hsf301.project.model.response.ApiResponse;
import com.hsf301.project.model.wallet.Wallet;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/get-balance")
    public ResponseEntity<BigDecimal> getBalance(@RequestParam("userId") Long userId) {
        Wallet wallet = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok(wallet.getBalance());
    }

    @PostMapping("/deposit")
    public ResponseEntity deposit(@RequestBody WalletDepositRequest data) {
        try {
            String paymentUrl = walletService.createPaymentUrl(
                    data.getUserId(),
                    data.getAmount(),
                    data.getRedirectUrl() // URL gốc do người dùng gửi
            );
            return ResponseEntity.ok().body(new ApiResponse<>(URI.create(paymentUrl)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating payment URL");
        }
    }

    @GetMapping("/confirm-deposit")
    public ResponseEntity<String> confirmDeposit(
            @RequestParam String redirectUrl,
            @RequestParam Map<String, String> allParams) {
    
        boolean isValid = walletService.isValidTransaction(allParams);
        if (isValid) {
            walletService.confirmAndAddTransaction(allParams);
            return ResponseEntity.status(302).location(URI.create(redirectUrl)).build();
        } else {
            return ResponseEntity.status(400).body("Invalid transaction");
        }
    }
}
