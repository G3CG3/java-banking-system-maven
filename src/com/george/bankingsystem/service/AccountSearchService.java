package com.george.bankingsystem.service;

import com.george.bankingsystem.exceptions.AccountNotFoundException;
import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;

import java.util.List;

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

        /*
        for (Account acc : accounts) {
            if (acc.getId() == id)
                return acc;
        }
        throw new AccountNotFoundException("The account with id " + id + " could not be found");
         */
    }

    public List<Account> findAccountsByOwner(String ownerName) {
        return bank.getAccounts().stream()
                .filter(account -> account.getOwnerName().equalsIgnoreCase(ownerName))
                .toList();
    }
}
