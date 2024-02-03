package com.kaveesha.edu.view.tm;

import java.time.LocalDate;

public class IncomeTM {
    private LocalDate date;
    private double amount;

    public IncomeTM() {
    }

    public IncomeTM(LocalDate date, double amount) {
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
        return "IncomeTM{" +
                "date=" + date +
                ", amount=" + amount +
                '}';
    }
}
