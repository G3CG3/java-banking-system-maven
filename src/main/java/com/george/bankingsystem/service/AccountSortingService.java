package com.george.bankingsystem.service;

import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;

import java.util.Comparator;
import java.util.List;

public class AccountSortingService {
    private final Bank bank;

    public AccountSortingService(Bank bank) {
        this.bank = bank;
    }

    public List<Account> getAccountsSortedByBalance() {
        return bank.getAccounts().stream()
                .sorted(Comparator.comparing(Account::getBalance).reversed())
                .toList();
    }

    public List<Account> getAccountsSortedByOwnerName() {
        return bank.getAccounts().stream()
                .sorted(Comparator.comparing(Account::getOwnerName, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
}
