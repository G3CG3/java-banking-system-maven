package com.george.bankingsystem.model;


import java.time.LocalDateTime;

public class Transaction {
    private final int id;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;

    public Transaction(int id, TransactionType type, double amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}