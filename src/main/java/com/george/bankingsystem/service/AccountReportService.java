package com.george.bankingsystem.service;

import com.george.bankingsystem.model.Account;

import java.util.stream.Collectors;

public class AccountReportService {
    private final Account account;
    private final TransactionAnalysisService analysisService;

    public AccountReportService(Account account) {
        this.account = account;
        this.analysisService = new TransactionAnalysisService(account);
    }

    public String generateSummary() {
        return "Account #" + account.getId() +
                "\nOwner: " + account.getOwnerName() +
                "\nTransactions: " + analysisService.getTransactionCount() +
                "\nTotal Deposits: " + analysisService.getTotalDeposits() +
                "\nTotal Withdrawals: " + analysisService.getTotalWithdrawals() +
                "\nAverage Transaction: " + analysisService.getAverageTransactionAmount();
    }

    public String generateStatistics() {
        if (analysisService.getTransactionCount() == 0) {
            return "No transactions available.";
        }

        return "Transaction totals:\n" +
                analysisService.getTotalAmountPerTransactionType()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }
}