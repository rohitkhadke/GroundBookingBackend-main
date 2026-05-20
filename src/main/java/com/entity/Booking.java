package com.entity;

import java.time.LocalDateTime;

import com.enums.BookingStatus;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 🔹 Relations
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ground_id")
    private Ground ground;

    // 🔹 Booking Time
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // 🔹 Pricing
    private double totalAmount;

    // 🔹 Payment
    private boolean isPaid = false;

    // 🔹 Status (ENUM)
    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.BOOKED;

    // 🔹 Created Time
    private LocalDateTime createdAt;
    
    private String paymentId;

    private String paymentStatus;
    // PAID
    // REFUND_PENDING
    // REFUNDED

    private LocalDateTime refundedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}