package com.hsf301.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hsf301.project.model.transactionHistory.TransactionHistory;
import com.hsf301.project.model.user.User;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {
   TransactionHistory findByUserAndAmountAndStatus(User user, double amount, String status);
   List<TransactionHistory> findByUser(User user);
}
