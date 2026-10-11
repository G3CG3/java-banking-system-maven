package com.george.bankingsystem.service;

import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;

import java.util.Comparator;

public class BankAnalysisService {
    private final Bank bank;

    public BankAnalysisService(Bank bank) {
        this.bank = bank;
    }

    public double getTotalBankBalance() {
        return bank.getAccounts().stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    public double getAverageBalance() {
        return bank.getAccounts().stream()
                .mapToDouble(Account::getBalance)
                .average()
                .orElse(0.0);
    }

    public double getHighestBalance() {
        return bank.getAccounts().stream()
                .mapToDouble(Account::getBalance)
                .max()
                .orElse(0.0);
    }

    public double getLowestBalance() {
        return bank.getAccounts().stream()
                .mapToDouble(Account::getBalance)
                .min()
                .orElse(0.0);
    }

    public Account getRichestAccount() {
        return bank.getAccounts().stream()
                .max(Comparator.comparing(Account::getBalance))
                .orElseThrow(() -> new IllegalStateException("There are no accounts in the system."));
    }

    public long getAccountsCount() {
        return bank.getAccounts().size();
    }

    public long getAccountsWithPositiveBalance() {
        return bank.getAccounts().stream()
                .filter(account -> account.getBalance() > 0)
                .count();
    }
}
