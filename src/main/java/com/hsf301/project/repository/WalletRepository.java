package com.hsf301.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hsf301.project.model.wallet.Wallet;
import com.hsf301.project.model.user.User;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Wallet findByUser(User user);
}
