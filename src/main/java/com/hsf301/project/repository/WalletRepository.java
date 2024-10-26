package com.hsf301.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hsf301.project.model.wallet.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer>  {
    
}
