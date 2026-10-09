package com.george.bankingsystem.model;

import com.george.bankingsystem.exceptions.DuplicateAccountException;
import com.george.bankingsystem.service.AccountSearchService;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<Account> accounts;
    private final AccountSearchService accountSearchService;

    public Bank() {
        this.accounts = new ArrayList<>();
        this.accountSearchService = new AccountSearchService(this);
    }

    public void addAccount(Account account) {
        if (accounts.stream()
                .anyMatch(existingAccount -> existingAccount.getId() == account.getId())) {
            throw new DuplicateAccountException("An account with this id already exists");
        }
        accounts.add(account);
    }

    public void transfer(int fromAccountId, int toAccountId, double amount) {
        if (amount <= 0 || !Double.isFinite(amount)) {
            throw new IllegalArgumentException("Amount should be positive and finite");
        }
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Source and destination accounts must be different.");
        }
        Account toAccount  = accountSearchService.findAccountById(toAccountId);
        Account fromAccount = accountSearchService.findAccountById(fromAccountId);
        if (!Double.isFinite(fromAccount.getBalance() - amount)) throw new IllegalArgumentException("The balance of account " + fromAccountId + " will overflow.");
        if (fromAccount.getBalance() - amount < 0)  throw new IllegalArgumentException("The account " + fromAccountId + " has insufficient funds.");
        Transaction transactionOut = new Transaction(TransactionType.TRANSFER, amount, fromAccountId, toAccountId);
        Transaction transactionIn = new Transaction(TransactionType.TRANSFER, amount, fromAccountId, toAccountId);
        fromAccount.withdraw(amount, transactionOut);
        toAccount.deposit(amount, transactionIn);
    }

    public List<Account> getAccounts() {
        return List.copyOf(accounts);
    }

    public boolean hasAccounts() {
        return !accounts.isEmpty();
    }
}
