package com.hsf301.project.model.wallet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.hsf301.project.model.user.User;

@Data
@Entity
@Table(name = "Wallet")
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer walletId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Column(nullable = false)
    private Double balance = 0.0;

    @Column(nullable = false)
    private LocalDateTime lastUpdated = LocalDateTime.now();
}
