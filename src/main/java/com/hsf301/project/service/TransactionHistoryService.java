package com.hsf301.project.service;

import com.hsf301.project.model.request.TransactionHistoryGetTransactionRequest;
import com.hsf301.project.model.response.TransactionHistoryGetTransactionResponse;
import com.hsf301.project.model.response.TransactionHistoryGetTransactionResponseContainer;
import com.hsf301.project.model.transactionHistory.TransactionHistory;
import com.hsf301.project.model.user.User;
import com.hsf301.project.repository.TransactionHistoryRepository;
import com.hsf301.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionHistoryService {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    public TransactionHistoryGetTransactionResponseContainer getTransactionsByUserId(
            TransactionHistoryGetTransactionRequest request) {
        User user = userRepository.findByUserId(request.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("User not found for ID: " + request.getUserId());
        }

        List<TransactionHistory> sortedTransactions = transactionHistoryRepository.findByUser(user).stream()
                .sorted((t1, t2) -> t2.getCreatedDate().compareTo(t1.getCreatedDate()))
                .collect(Collectors.toList());

        int totalFound = sortedTransactions.size();
        int pageSize = request.getResultCount();
        int totalPages = (int) Math.ceil((double) totalFound / pageSize);

        // Điều chỉnh tính toán fromIndex để tránh vượt quá giới hạn
        int fromIndex = Math.min((request.getPage() - 1) * pageSize, totalFound);
        int toIndex = Math.min(fromIndex + pageSize, totalFound);

        // Debug: Print to check index bounds
        System.out.println("fromIndex: " + fromIndex + ", toIndex: " + toIndex);

        if (fromIndex >= totalFound || fromIndex < 0) {
            // Trả về danh sách rỗng nếu fromIndex không hợp lệ
            return new TransactionHistoryGetTransactionResponseContainer(Collections.emptyList(), totalPages);
        }

        List<TransactionHistory> paginatedTransactions = sortedTransactions.subList(fromIndex, toIndex);

        List<TransactionHistoryGetTransactionResponse> transactionResponses = paginatedTransactions.stream()
                .map(transaction -> new TransactionHistoryGetTransactionResponse(
                        transaction.getTransactionId(),
                        transaction.getTransactionType(),
                        BigDecimal.valueOf(transaction.getAmount()).toPlainString(),
                        transaction.getPaymentMethod(),
                        transaction.getCreatedDate(),
                        transaction.getCompletionTime(),
                        transaction.getStatus()))
                .collect(Collectors.toList());

        return new TransactionHistoryGetTransactionResponseContainer(transactionResponses, totalPages);
    }

}
