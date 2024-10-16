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
    private Integer couponId;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    private LocalDateTime availableDate;
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private Integer usageCount = 0;

    private Integer maxUsage;

    @Column(length = Integer.MAX_VALUE)
    private String usersUsed;
}
