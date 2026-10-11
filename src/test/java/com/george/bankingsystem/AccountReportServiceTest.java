package com.george.bankingsystem;

import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.service.AccountReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountReportServiceTest {
    private AccountReportService  accountReportService;

    @BeforeEach
    void setup() {
        Account account = new Account(1, "George", 0.0);
        this.accountReportService = new AccountReportService(account);
    }

    @Test
    void shouldReturnMessageForEmptyAccount() {
        assertEquals("No transactions available.", accountReportService.generateStatistics());
    }

    @Test
    void shouldReturnAccountSummaryReport() {
        String report = accountReportService.generateSummary();

        assertAll(
                () -> assertTrue(report.contains("Account #1")),
                () -> assertTrue(report.contains("Owner: George")),
                () -> assertTrue(report.contains("Transactions: 0")),
                () -> assertTrue(report.contains("Total Deposits: 0.0")),
                () -> assertTrue(report.contains("Total Withdrawals: 0.0")),
                () -> assertTrue(report.contains("Average Transaction: 0.0"))
        );
    }

    @Test
    void shouldReturnTransactionStatisticsWithTransactions() {
        Account account1 = new Account(2, "George", 3000.0);
        account1.deposit(1000.0);
        account1.withdraw(500.0);
        AccountReportService service = new AccountReportService(account1);

        String report = service.generateStatistics();

        assertAll(
                () -> assertTrue(report.contains("DEPOSIT: 1000.0")),
                () -> assertTrue(report.contains("WITHDRAW: 500.0"))
        );
    }
}