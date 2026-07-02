package com.george.bankingsystem.model;

import com.george.bankingsystem.exceptions.DuplicateAccountException;
import com.george.bankingsystem.service.AccountSearchService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Bank {
    private final List<Account> accounts;
    private final AccountSearchService accountSearchService;

    public Bank() {
        this.accounts = new ArrayList<>();
        this.accountSearchService = new AccountSearchService(this);
    }

    public void addAccount(Account account) {
        /*
        boolean exists = accounts.stream()
                        .anyMatch(existingAccount -> existingAccount.getId() == account.getId());
        if (exists) throw new DuplicateAccountException("An account with this id already exists");
        */

        if (accounts.stream()
                .anyMatch(existingAccount -> existingAccount.getId() == account.getId())) {
            throw new DuplicateAccountException("An account with this id already exists");
        }

        /*
        for (Account existingAccount : accounts) {
            if (existingAccount.getId() == account.getId()) throw new DuplicateAccountException("An account with this id already exists");
        }
        */
        accounts.add(account);
    }

    public void transfer(int fromAccountId, int toAccountId, double amount) {
        Account toAccount  = accountSearchService.findAccountById(toAccountId);
        Account fromAccount = accountSearchService.findAccountById(fromAccountId);
        Transaction transactionOut = new Transaction(TransactionType.TRANSFER, amount, fromAccountId, toAccountId);
        Transaction transactionIn = new Transaction(TransactionType.TRANSFER, amount, fromAccountId, toAccountId);
        toAccount.deposit(amount, transactionIn);
        fromAccount.withdraw(amount, transactionOut);
    }

    public double getTotalBankBalance() {
        return accounts.stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    public Account getRichestAccount() {
        return accounts.stream()
                .max(Comparator.comparing(Account::getBalance))
                .orElseThrow(() -> new IllegalStateException("There are no accounts in the system."));
    }

    public double getAverageBalance() {
        return accounts.stream()
                .mapToDouble(Account::getBalance)
                .average()
                .orElse(0.0);
    }

    public long getAccountsCount() {
        return accounts.stream()
                .count();
    }

    public long getAccountsWithPositiveBalance() {
        return accounts.stream()
                .filter(account -> account.getBalance() > 0)
                .count();
    }

    public double getHighestBalance() {
        return accounts.stream()
                .mapToDouble(Account::getBalance)
                .max()
                .orElse(0.0);
    }

    public double getLowestBalance() {
        return accounts.stream()
                .mapToDouble(Account::getBalance)
                .min()
                .orElse(0.0);
    }

    public List<Account> getAccounts() {
        return List.copyOf(accounts);
    }
}
