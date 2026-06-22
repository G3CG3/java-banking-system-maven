package com.george.bankingsystem.model;

import com.george.bankingsystem.exceptions.InsufficientFundsException;

public class Account {
    private final int id;
    private final String ownerName;
    private double balance;

    public Account(int id, String ownerName, double balance) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
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
    }

    public void withdraw(double amount) {
        if (isInvalidAmount(amount)) {
            throw new IllegalArgumentException("The withdrawing amount should be a positive number");
        }
        if (balance < amount) {
            throw new InsufficientFundsException("You don't have sufficient funds in your account for the withdrawal");
        }
        balance -= amount;
    }

    private boolean isInvalidAmount(double amount) {
        return amount <= 0;
    }
}
