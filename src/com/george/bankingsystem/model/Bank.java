package com.george.bankingsystem.model;

import com.george.bankingsystem.exceptions.AccountNotFoundException;
import com.george.bankingsystem.exceptions.DuplicateAccountException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Bank {
    private final List<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
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

    public Account findAccountById(int id) {
       return accounts.stream()
               .filter(a -> a.getId() == id)
               .findFirst()
               .orElseThrow(() -> new AccountNotFoundException("The account with id " + id + " could not be found"));

        /*
        for (Account acc : accounts) {
            if (acc.getId() == id)
                return acc;
        }
        throw new AccountNotFoundException("The account with id " + id + " could not be found");
         */
    }

    public void transfer(int fromAccountId, int toAccountId, double amount) {
        Account toAccount  = findAccountById(toAccountId);
        Account fromAccount = findAccountById(fromAccountId);
        Transaction transactionOut = new Transaction(TransactionType.TRANSFER, amount, fromAccountId, toAccountId);
        Transaction transactionIn = new Transaction(TransactionType.TRANSFER, amount, fromAccountId, toAccountId);
        toAccount.deposit(amount, transactionIn);
        fromAccount.withdraw(amount, transactionOut);
    }

    public List<Account> findAccountsByOwner(String ownerName) {
        return accounts.stream()
                .filter(account -> account.getOwnerName().equalsIgnoreCase(ownerName))
                .toList();
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

    public List<Account> getAccountsSortedByBalance() {
        return accounts.stream()
                .sorted(Comparator.comparing(Account::getBalance).reversed())
                .toList();
    }

    public List<Account> getAccountsSortedByOwnerName() {
        return accounts.stream()
                .sorted(Comparator.comparing(Account::getOwnerName, String.CASE_INSENSITIVE_ORDER))
                .toList();
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
}
