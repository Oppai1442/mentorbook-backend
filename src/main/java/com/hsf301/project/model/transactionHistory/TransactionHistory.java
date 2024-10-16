package com.hsf301.project.model.transactionHistory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.hsf301.project.model.user.User;

@Data
@Entity
@Table(name = "TransactionHistory")
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Column(nullable = false, length = 20)
    private String transactionType;

    @Column(nullable = false)
    private Double amount;

    @Column(length = 50)
    private String paymentMethod;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime completionTime;
}
