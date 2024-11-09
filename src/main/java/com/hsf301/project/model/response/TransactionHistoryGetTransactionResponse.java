package com.hsf301.project.model.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryGetTransactionResponse {
    private Integer transactionId;
    private String transactionType;
    private String amount;
    private String paymentMethod;
    private LocalDateTime createdDate;
    private LocalDateTime completionTime;
    private String status;
}
