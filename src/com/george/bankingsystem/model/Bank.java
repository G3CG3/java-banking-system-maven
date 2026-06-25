package com.george.bankingsystem.model;

import com.george.bankingsystem.exceptions.AccountNotFoundException;
import com.george.bankingsystem.exceptions.DuplicateAccountException;

import java.util.ArrayList;
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
}
