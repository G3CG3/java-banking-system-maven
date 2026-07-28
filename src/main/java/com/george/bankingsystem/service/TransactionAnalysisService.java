package com.george.bankingsystem.service;

import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Transaction;
import com.george.bankingsystem.model.TransactionType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionAnalysisService {
    private final Account account;

    public TransactionAnalysisService(Account account) {
        this.account = account;
    }

    public double getTotalDeposits() {
        return account.getTransactions().stream()
                .filter(t -> t.getType() == TransactionType.DEPOSIT )
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalWithdrawals() {
        return account.getTransactions().stream()
                .filter(t -> t.getType() == TransactionType.WITHDRAW)
                .mapToDouble((Transaction::getAmount))
                .sum();
    }

    public int getTransactionCount() {
        return account.getTransactions().size();
    }

    public double getAverageTransactionAmount() {
        return account.getTransactions().stream()
                .mapToDouble(Transaction::getAmount)
                .average()
                .orElse(0.0); // Throw exception is an alternative, but new accounts can have 0.0 as average
    }

    public Map<TransactionType, List<Transaction>> groupTransactionsByType() {
        return account.getTransactions().stream()
                .collect(Collectors.groupingBy(Transaction::getType));
    }

    public Map<TransactionType, Double> getTotalAmountPerTransactionType() {
        return account.getTransactions().stream()
                .collect(Collectors.groupingBy(Transaction::getType, Collectors.summingDouble(Transaction::getAmount)));
    }
}
