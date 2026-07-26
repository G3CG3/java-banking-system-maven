package com.george.bankingsystem.service;

import com.george.bankingsystem.model.Bank;

import java.util.stream.Collectors;

public class BankReportService {
    private final Bank bank;
    private final AccountSearchService searchService;

    public BankReportService(Bank bank) {
        this.bank = bank;
        this.searchService = new AccountSearchService(bank);
    }

    public String generateBankSummary() {
        return "===== BANK SUMMARY =====" +
                "\nAccounts: " + bank.getAccountsCount() +
                "\nTotal Balance: " + bank.getTotalBankBalance() +
                "\nAverage Balance: " + bank.getAverageBalance() +
                "\nRichest Account: " +
                "\n" + bank.getRichestAccount() +
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