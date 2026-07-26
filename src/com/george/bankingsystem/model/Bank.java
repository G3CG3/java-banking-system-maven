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

    public List<Account> getAccounts() {
        return List.copyOf(accounts);
    }

    public boolean hasAccounts() {
        return !accounts.isEmpty();
    }
}
