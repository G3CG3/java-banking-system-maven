package com.george.bankingsystem;

import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Transaction;
import com.george.bankingsystem.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        this.account = new Account(1, "George", 1000.0);
    }

    @Test
    void shouldCreateValidAccount() {
        assertAll("Account creation check:",
                () -> assertEquals(1, account.getId(), "Incorrect ID"),
                () -> assertEquals("George", account.getOwnerName(), "Incorrect owner name"),
                () -> assertEquals(1000.0, account.getBalance(), "Incorrect balance value"),
                () -> assertTrue(account.getTransactions().isEmpty(), "A new account should have no previous transactions"));
    }

    @ParameterizedTest
    @CsvSource({
            "100.0, 1100.0",
            "0.01, 1000.01",
            "1.0, 1001.0",
            "1000.0, 2000.0"
    })
    void shouldIncreaseBalanceAfterDeposit(double amount, double expectedBalance) {
        account.deposit(amount);

        Transaction transaction = account.getTransactions().getFirst();

        assertAll("Deposit check:",
            () -> assertEquals(amount,  transaction.getAmount(), "Incorrect transaction value"),
            () -> assertEquals(TransactionType.DEPOSIT, transaction.getType()),
            () -> assertEquals(expectedBalance, account.getBalance(), "Incorrect balance value")
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {-100.0, -1.0, -0.01, 0.0})
    void shouldThrowExceptionWhenDepositAmountIsInvalid(double amount) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> account.deposit(amount));

        assertEquals("The depositing amount should be a positive number", exception.getMessage());
    }

    @Test
    void shouldNotChangeBalanceWhenDepositFails() {
        int size = account.getTransactions().size();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> account.deposit(-100.0));

        assertAll(
            () -> assertEquals("The depositing amount should be a positive number", exception.getMessage()),
            () -> assertEquals(size, account.getTransactions().size(), "Failed transactions should not be taken into account"),
            () -> assertEquals(1000.0,  account.getBalance(), "Incorrect balance value")
        );
    }
}
