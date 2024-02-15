package com.kaveesha.edu.dto;

import java.time.LocalDate;

public class IncomeDto {
    private LocalDate date;
    private double amount;

    public IncomeDto() {
    }

    public IncomeDto(LocalDate date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Income{" +
                "date=" + date +
                ", amount=" + amount +
                '}';
    }
}
