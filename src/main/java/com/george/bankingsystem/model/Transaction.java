package com.george.bankingsystem.model;


import java.time.LocalDateTime;

public class Transaction {
    private static int nextId = 1;

    private final int id;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final Integer fromAccountId;
    private final Integer toAccountId;

    public Transaction(TransactionType type, double amount) {
        this(type, amount, null, null);
    }

    public Transaction(TransactionType type, double amount, Integer fromAccountId, Integer toAccountId) {
        if (type == null) {
            throw new IllegalArgumentException("TransactionType cannot be null.");
        }
        if (amount <= 0 || !Double.isFinite(amount))  {
            throw new IllegalArgumentException("The amount must be non-negative and finite.");
        }
        this.id = nextId++;
        this.type = type;
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
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

    public Integer getFromAccountId() {
        return fromAccountId;
    }

    public Integer getToAccountId() {
        return toAccountId;
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