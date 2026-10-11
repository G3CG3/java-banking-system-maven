package com.george.bankingsystem;

import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;
import com.george.bankingsystem.service.BankReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankReportServiceTest {
    private BankReportService bankReportService;
    private Bank bank;

    @BeforeEach
    void setup() {
        this.bank = new Bank();
        this.bankReportService = new BankReportService(bank);
    }

    @Test
    void shouldReturnMessageForEmptyBank() {
        assertEquals("No accounts available.", bankReportService.generateOwnerReport());
    }

    @Test
    void shouldReturnBankSummary() {
        Account account1 = new Account(1, "George", 1000.0);
        Account account2 = new Account(2, "Jane", 2000.0);

        bank.addAccount(account1);
        bank.addAccount(account2);

        String summary = bankReportService.generateBankSummary();

        assertAll(
                () -> assertTrue(summary.contains("Accounts: 2")),
                () -> assertTrue(summary.contains("Total Balance: 3000.0")),
                () -> assertTrue(summary.contains("Average Balance: 1500.0")),
                () -> assertTrue(summary.contains("id=2"))
        );
    }

    @Test
    void shouldThrowExceptionWhenGeneratingSummaryForEmptyBank() {
        assertThrows(IllegalStateException.class, () -> bankReportService.generateBankSummary());
    }

    @Test
    void shouldReturnOwnerReport() {
        bank.addAccount(new Account(1, "George", 1000.0));
        bank.addAccount(new Account(2, "George", 2000.0));
        bank.addAccount(new Account(3, "Jane", 1500.0));

        String report = bankReportService.generateOwnerReport();

        assertAll(
                () -> assertTrue(report.contains("George -> 2 accounts")),
                () -> assertTrue(report.contains("Jane -> 1 accounts"))
        );
    }
}
