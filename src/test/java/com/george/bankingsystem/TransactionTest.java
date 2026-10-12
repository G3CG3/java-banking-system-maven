package com.george.bankingsystem;

import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Transaction;
import com.george.bankingsystem.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    @BeforeEach
    public void setUp() {
        Account account1 = new Account(1, "George", 1000);
        Account account2 = new Account(2, "Jim", 2000);
    }

    @Test
    void shouldRejectInvalidTransactionArguments() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Transaction(null, 100, 1, 2)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Transaction(TransactionType.DEPOSIT, 0, 1, 2)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Transaction(TransactionType.DEPOSIT, -100, 1, 2)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Transaction(TransactionType.DEPOSIT, Double.NaN, 1, 2)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Transaction(TransactionType.DEPOSIT, Double.POSITIVE_INFINITY, 1, 2)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Transaction(TransactionType.DEPOSIT, Double.NEGATIVE_INFINITY, 1, 2))
        );
    }

    @Test
    void shouldReturnAValidTransaction() {
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, 500.0, 1, 2);

        assertAll(
                () -> assertEquals(TransactionType.DEPOSIT, transaction.getType()),
                () -> assertEquals(500.0, transaction.getAmount()),
                () -> assertEquals(1, (int) transaction.getFromAccountId()),
                () -> assertEquals(2, (int) transaction.getToAccountId()),
                () -> assertTrue(transaction.getId() > 0),
                () -> assertNotNull(transaction.getTimestamp())
        );
    }
}