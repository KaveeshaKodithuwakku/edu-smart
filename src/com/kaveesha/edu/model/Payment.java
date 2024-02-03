package com.kaveesha.edu.model;

import java.time.LocalDate;

public class Payment {
    private Long payment_id;
    private LocalDate payDate;
    private boolean isVerified;
    private double amount;
    private Long registration_id;

    public Payment() {
    }

    public Payment(Long payment_id, LocalDate payDate, boolean isVerified, double amount, Long registration_id) {
        this.payment_id = payment_id;
        this.payDate = payDate;
        this.isVerified = isVerified;
        this.amount = amount;
        this.registration_id = registration_id;
    }

    public Long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Long payment_id) {
        this.payment_id = payment_id;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(Long registration_id) {
        this.registration_id = registration_id;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "payment_id=" + payment_id +
                ", payDate=" + payDate +
                ", isVerified=" + isVerified +
                ", amount=" + amount +
                ", registration_id=" + registration_id +
                '}';
    }
}
