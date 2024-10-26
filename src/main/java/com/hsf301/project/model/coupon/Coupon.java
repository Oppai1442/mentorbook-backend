package com.hsf301.project.model.coupon;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Coupons")
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Integer couponId;

    @Column(name="code",nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "available_date", nullable = false)
    private LocalDateTime availableDate;
    
    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name="usage_count",nullable = false)
    private Integer usageCount = 0;

    @Column(name="max_usage",nullable = false)
    private Integer maxUsage;

    @Column(name="user_used",length = Integer.MAX_VALUE)
    private String usersUsed;
}
