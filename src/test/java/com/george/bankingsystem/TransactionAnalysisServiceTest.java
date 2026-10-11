package com.george.bankingsystem;

import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Transaction;
import com.george.bankingsystem.model.TransactionType;
import com.george.bankingsystem.service.TransactionAnalysisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionAnalysisServiceTest {
    private TransactionAnalysisService transactionAnalysisService;

    @BeforeEach
    public void setup() {
        Account account = new Account(1, "George", 1000.0);

        account.deposit(500.0);
        account.withdraw(1000.0);
        account.deposit(300.0);

        this.transactionAnalysisService = new TransactionAnalysisService(account);
    }

    @Test
    void shouldReturnTheAmountOfTotalDeposits() {
        double totalDeposits = transactionAnalysisService.getTotalDeposits();

        assertEquals(800.0, totalDeposits);
    }

    @Test
    void shouldReturnTheAmountOfTotalWithdrawals() {
        double totalWithdrawals = transactionAnalysisService.getTotalWithdrawals();

        assertEquals(1000.0, totalWithdrawals);
    }

    @Test
    void shouldReturnTheTotalAmountOfTransactions() {
        int totalTransactions = transactionAnalysisService.getTransactionCount();

        assertEquals(3, totalTransactions);
    }

    @Test
    void shouldReturnTheAverageAmountOfTransactions() {
        double averageAmount =  transactionAnalysisService.getAverageTransactionAmount();

        assertEquals(600.0, averageAmount);
    }

    @Test
    void shouldReturnGroupedTransactionsByType() {
        Map<TransactionType, List<Transaction>> map =  transactionAnalysisService.groupTransactionsByType();

        assertAll("Grouped Map Assertions",
                () -> assertTrue(map.containsKey(TransactionType.DEPOSIT), "Map should contain key TransactionType.DEPOSIT"),
                () -> assertTrue(map.containsKey(TransactionType.WITHDRAW), "Map should contain key TransactionType.WITHDRAW"),
                () -> assertEquals(List.of(500.0, 300.0), map.get(TransactionType.DEPOSIT).stream().map(Transaction::getAmount).toList()),
                () -> assertEquals(List.of(1000.0), map.get(TransactionType.WITHDRAW).stream().map(Transaction::getAmount).toList())
        );
    }

    @Test
    void shouldReturnTheTotalAmountOfTransactionsPerType() {
        Map<TransactionType, Double> map =  transactionAnalysisService.getTotalAmountPerTransactionType();

        assertAll("Grouped Map Assertions",
                () -> assertTrue(map.containsKey(TransactionType.DEPOSIT), "Map should contain key TransactionType.DEPOSIT"),
                () -> assertTrue(map.containsKey(TransactionType.WITHDRAW), "Map should contain key TransactionType.WITHDRAW"),
                () -> assertEquals(800.0, map.get(TransactionType.DEPOSIT)),
                () -> assertEquals(1000.0, map.get(TransactionType.WITHDRAW))
        );
    }
}