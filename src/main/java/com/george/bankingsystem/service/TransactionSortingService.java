package com.george.bankingsystem.service;

import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Transaction;

import java.util.Comparator;
import java.util.List;

public class TransactionSortingService {
    private final Account account;

    public TransactionSortingService(Account account) {
        this.account = account;
    }

    public List<Transaction> getTransactionsSortedByAmount() {
        return account.getTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .toList();
    }

    public List<Transaction> getTransactionsSortedByDate() {
        return account.getTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .toList();
    }
}
