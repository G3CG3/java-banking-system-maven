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
        return transactions;
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
        if (isInvalidAmount(amount)) {
            throw new IllegalArgumentException("The depositing amount should be a positive number");
        }
        balance += amount;
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, amount);
        transactions.add(transaction);
    }

    public void withdraw(double amount) {
        if (isInvalidAmount(amount)) {
            throw new IllegalArgumentException("The withdrawing amount should be a positive number");
        }
        if (balance < amount) {
            throw new InsufficientFundsException("You don't have sufficient funds in your account for the withdrawal");
        }
        balance -= amount;
        Transaction transaction = new Transaction(TransactionType.WITHDRAW, amount);
        transactions.add(transaction);
    }

    private boolean isInvalidAmount(double amount) {
        return amount <= 0;
    }
}
