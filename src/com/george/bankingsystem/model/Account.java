package com.george.bankingsystem.model;

import com.george.bankingsystem.exceptions.InsufficientFundsException;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final int id;
    private final String ownerName;
    private double balance;
    private final List<Transaction> transactions;

    public Account(int id, String ownerName, double balance) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }

    public List<Transaction> getDeposits() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.DEPOSIT)
                .toList();
    }

    public List<Transaction> getWithdrawals() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.WITHDRAW)
                .toList();
    }

    public List<Transaction> getTransfers() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.TRANSFER)
                .toList();
    }

    public List<Transaction> getTransactionsAbove(double amount) {
        return transactions.stream()
                .filter(t -> t.getAmount() > amount)
                .toList();
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", ownerName='" + ownerName + '\'' +
                ", balance=" + balance +
                '}';
    }

    public void deposit(double amount) {
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, amount);
        this.deposit(amount, transaction);
    }

    void deposit(double amount, Transaction transaction) {
        if (isInvalidAmount(amount)) {
            throw new IllegalArgumentException("The depositing amount should be a positive number");
        }
        balance += amount;
        transactions.add(transaction);
    }

    public void withdraw(double amount) {
        Transaction transaction = new Transaction(TransactionType.WITHDRAW, amount);
        this.withdraw(amount, transaction);
    }

    void withdraw(double amount, Transaction transaction) {
        if (isInvalidAmount(amount)) {
            throw new IllegalArgumentException("The withdrawing amount should be a positive number");
        }
        if (balance < amount) {
            throw new InsufficientFundsException("You don't have sufficient funds in your account for the withdrawal");
        }
        balance -= amount;
        transactions.add(transaction);
    }

    private boolean isInvalidAmount(double amount) {
        return amount <= 0;
    }

    public double getTotalDeposits() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.DEPOSIT )
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalWithdrawals() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.WITHDRAW)
                .mapToDouble((Transaction::getAmount))
                .sum();
    }

    public int getTransactionCount() {
        return transactions.size();
    }

    public double getAverageTransactionAmount() {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .average()
                .orElse(0.0); // Throw exception is an alternative, but new accounts can have 0.0 as average
    }
}
