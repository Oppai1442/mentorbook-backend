package com.hsf301.project.controller;

import com.hsf301.project.model.request.TransactionHistoryGetTransactionRequest;
import com.hsf301.project.model.response.TransactionHistoryGetTransactionResponseContainer;
import com.hsf301.project.service.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionHistoryController {

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @PostMapping("/get-transaction")
    public TransactionHistoryGetTransactionResponseContainer getTransactionHistory(@RequestBody TransactionHistoryGetTransactionRequest data) {
        return transactionHistoryService.getTransactionsByUserId(data);
    }
}
