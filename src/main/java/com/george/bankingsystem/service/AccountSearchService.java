package com.george.bankingsystem.service;

import com.george.bankingsystem.exceptions.AccountNotFoundException;
import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountSearchService {
    private final Bank bank;

    public AccountSearchService(Bank bank) {
        this.bank = bank;
    }

    public Account findAccountById(int id) {
        return bank.getAccounts().stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("The account with id " + id + " could not be found"));
    }

    public List<Account> findAccountsByOwner(String ownerName) {
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner's name cannot be null or blank");
        }
        return bank.getAccounts().stream()
                .filter(account -> account.getOwnerName().equalsIgnoreCase(ownerName))
                .toList();
    }

    public List<Account> findAccountsWithBalanceAbove(double amount) {
        return bank.getAccounts().stream()
                .filter(account -> account.getBalance() > amount)
                .toList();
    }

    public List<Account> findAccountsByOwnerWithBalanceAbove(
            String ownerName,
            double amount
    ) {
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner's name cannot be null or blank");
        }
        return bank.getAccounts().stream()
                .filter(account -> account.getOwnerName().equalsIgnoreCase(ownerName) && account.getBalance() > amount)
                .toList();
    }

    public Map<String, List<Account>> groupAccountsByOwner() {
        return bank.getAccounts().stream()
                .collect(Collectors.groupingBy(Account::getOwnerName));
    }

    public Map<Boolean, List<Account>> partitionAccountsByPositiveBalance() {
        return bank.getAccounts().stream()
                .collect(Collectors.partitioningBy(a -> a.getBalance() > 0));
    }
}
