package com.patika.model;

import java.time.LocalDateTime;

public class Bill {

    private double amount;
    private LocalDateTime time;

    public Bill() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "amount=" + amount +
                ", time=" + time +
                '}';
    }
}
