package com.george.bankingsystem.service;

import com.george.bankingsystem.model.Bank;

import java.util.stream.Collectors;

public class BankReportService {
    private final BankAnalysisService analysisService;
    private final AccountSearchService searchService;

    public BankReportService(Bank bank) {
        this.analysisService = new BankAnalysisService(bank);
        this.searchService = new AccountSearchService(bank);
    }

    public String generateBankSummary() {
        return "===== BANK SUMMARY =====" +
                "\nAccounts: " + analysisService.getAccountsCount() +
                "\nTotal Balance: " + analysisService.getTotalBankBalance() +
                "\nAverage Balance: " + analysisService.getAverageBalance() +
                "\nRichest Account: " +
                "\n" + analysisService.getRichestAccount() +
                "\n========================";
    }

    public String generateOwnerReport() {
        return searchService.groupAccountsByOwner()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " -> " + entry.getValue().size() + " accounts")
                .collect(Collectors.joining("\n"));
    }
}