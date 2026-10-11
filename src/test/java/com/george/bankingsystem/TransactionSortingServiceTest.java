package com.george.bankingsystem;

import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Transaction;
import com.george.bankingsystem.service.TransactionSortingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionSortingServiceTest {
    private TransactionSortingService transactionSortingService;

    @BeforeEach
    void setup() {
        Account account = new Account(1, "George", 2000);
        this.transactionSortingService = new TransactionSortingService(account);
        account.deposit(500.0);
        account.withdraw(1500.0);
        account.deposit(1000.0);
        account.withdraw(100.0);
    }

    @Test
    void shouldReturnSortedTransactionsByAmount() {
        List<Transaction> sortedTransactions = transactionSortingService.getTransactionsSortedByAmount();

        assertThat(sortedTransactions)
                .hasSize(4)
                .extracting(Transaction::getAmount)
                .containsExactly(1500.0, 1000.0, 500.0, 100.0);
    }

    @Test
    void shouldReturnSortedTransactionsByDate() {
        List<Transaction> sortedTransactions = transactionSortingService.getTransactionsSortedByDate();

        assertThat(sortedTransactions)
                .hasSize(4)
                .extracting(Transaction::getTimestamp)
                .isSortedAccordingTo(Comparator.reverseOrder());
    }
}
