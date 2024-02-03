package com.kaveesha.edu.model;

import java.time.LocalDate;

public class Registration {
    private int registrationId;
    private LocalDate regDate;
    private double amount;
    private Long intakeId;
    private Long studentId;

    public Registration() {
    }

    public Registration(int registrationId, LocalDate regDate, double amount, Long intakeId, Long studentId) {
        this.registrationId = registrationId;
        this.regDate = regDate;
        this.amount = amount;
        this.intakeId = intakeId;
        this.studentId = studentId;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(Long intakeId) {
        this.intakeId = intakeId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationId=" + registrationId +
                ", regDate=" + regDate +
                ", amount=" + amount +
                ", intakeId=" + intakeId +
                ", studentId=" + studentId +
                '}';
    }
}
