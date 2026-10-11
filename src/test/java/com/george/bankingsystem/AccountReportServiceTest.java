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
    void shouldReturnAccountSummaryReportForAccountWithNoTransactions() {
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

    @Test
    void shouldReturnAccountSummaryReport() {
        Account account2 = new Account(2, "Joe", 3000.0);
        account2.deposit(1000.0);
        account2.withdraw(500.0);
        AccountReportService service = new AccountReportService(account2);

        String report = service.generateSummary();

        assertAll(
                () -> assertTrue(report.contains("Account #2")),
                () -> assertTrue(report.contains("Owner: Joe")),
                () -> assertTrue(report.contains("Transactions: 2")),
                () -> assertTrue(report.contains("Total Deposits: 1000.0")),
                () -> assertTrue(report.contains("Total Withdrawals: 500.0")),
                () -> assertTrue(report.contains("Average Transaction: 750.0"))
        );
    }
}