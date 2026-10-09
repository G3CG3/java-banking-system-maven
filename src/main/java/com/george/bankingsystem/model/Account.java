package com.george.bankingsystem.model;

import com.george.bankingsystem.exceptions.InsufficientFundsException;
import com.george.bankingsystem.exceptions.InvalidAccountIdException;
import com.george.bankingsystem.exceptions.InvalidOwnerNameException;
import com.george.bankingsystem.exceptions.NegativeInitialBalanceException;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final int id;
    private final String ownerName;
    private double balance;
    private final List<Transaction> transactions;

    public Account(int id, String ownerName, double balance) {
        if (id <= 0) {
            throw new InvalidAccountIdException("Account id must be greater than zero.");
        }
        this.id = id;

        if (ownerName == null || ownerName.isBlank()) {
            throw new InvalidOwnerNameException("You must provide a name for the owner of the account.");
        }
        this.ownerName = ownerName;

        if (balance < 0 || !Double.isFinite(balance)) {
            throw new NegativeInitialBalanceException("The initial balance must be non-negative and finite.");
        }
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
        validateDeposit(amount);
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, amount);
        this.deposit(amount, transaction);
    }

    void deposit(double amount, Transaction transaction) {
        validateDeposit(amount);
        balance += amount;
        transactions.add(transaction);
    }

    public void withdraw(double amount) {
        validateWithdrawal(amount);
        Transaction transaction = new Transaction(TransactionType.WITHDRAW, amount);
        this.withdraw(amount, transaction);
    }

    void withdraw(double amount, Transaction transaction) {
        validateWithdrawal(amount);
        balance -= amount;
        transactions.add(transaction);
    }

    private boolean isInvalidAmount(double amount) {
        return amount <= 0 || !Double.isFinite(amount);
    }

    public List<Transaction> getTransactionsBetween(
            double min,
            double max
    ) {
        return transactions.stream()
                .filter(t -> t.getAmount() >= min && t.getAmount() <= max)
                .toList();
    }

    public boolean hasLargeTransaction(double amount) {
        return transactions.stream()
                .anyMatch(t -> t.getAmount() >= amount);
    }

    public boolean hasOnlyDeposits() {
        return transactions.stream()
                .allMatch(t -> t.getType() == TransactionType.DEPOSIT);
    }

    public boolean hasNoTransactions() {
        return transactions.stream()
                .findAny()
                .isEmpty();
    }

    public boolean hasTransactions() {
        return !transactions.isEmpty();
    }

    void validateDeposit(double amount) {
        if (isInvalidAmount(amount)) {
            throw new IllegalArgumentException("The depositing amount should be a positive number");
        }
        if (!Double.isFinite(balance + amount)) {
            throw new IllegalArgumentException("The new balance should be a finite number");
        }
    }

    void validateWithdrawal(double amount) {
        if (isInvalidAmount(amount)) {
            throw new IllegalArgumentException("The withdrawing amount should be a positive number");
        }
        if (balance < amount) {
            throw new InsufficientFundsException("You don't have sufficient funds in your account for the withdrawal");
        }
    }
}
