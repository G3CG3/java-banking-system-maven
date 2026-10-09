package com.george.bankingsystem;

import com.george.bankingsystem.exceptions.AccountNotFoundException;
import com.george.bankingsystem.exceptions.DuplicateAccountException;
import com.george.bankingsystem.exceptions.InsufficientFundsException;
import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;
import com.george.bankingsystem.model.Transaction;
import com.george.bankingsystem.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    private Bank bank;
    private Account account1;

    @BeforeEach
    void setUp() {
        this.bank = new Bank();
        this.account1 = new Account(1, "George", 1000.0);
    }

    @Test
    void shouldAddAccount() {
        int originalSize = bank.getAccounts().size();
        bank.addAccount(account1);
        List<Account> accounts = bank.getAccounts();
        Account addedAccount = accounts.getFirst();

        assertAll("Adding account to bank",
            () -> assertEquals(originalSize + 1, accounts.size(), "The size should be up by 1, since an account was added."),
            () -> assertEquals(account1, addedAccount, "The added account should be equal with the first one on our list of accounts")
        );
    }

    @Test
    void shouldThrowExceptionWhenAddingDuplicateAccount() {
        bank.addAccount(account1);
        int originalSize = bank.getAccounts().size();
        Account duplicate = new Account(1, "Maria",  5000.0);

        DuplicateAccountException exception = assertThrows(DuplicateAccountException.class, () -> bank.addAccount(duplicate));

        assertAll("Duplicate account check",
                () -> assertEquals("An account with this id already exists", exception.getMessage()),
                () -> assertEquals(originalSize, bank.getAccounts().size(), "The duplicate account should not be added in the bank")
        );
    }

    @Test
    void shouldTransferMoneyBetweenAccounts() {
        bank.addAccount(account1);

        Account account2 = new Account(2, "Maria", 2000.0);
        bank.addAccount(account2);

        double account1Balance = account1.getBalance();
        double account2Balance = account2.getBalance();

        bank.transfer(1, 2, 500);

        Transaction transactionIn = account2.getTransactions().getFirst();
        Transaction transactionOut = account1.getTransactions().getFirst();

        assertAll("Transfer check:",
                () -> assertEquals(account2Balance + 500, account2.getBalance(), "The account balance should equal with the account balance after transfer"),
                () -> assertEquals(account1Balance - 500, account1.getBalance(), "The account balance should equal with the account balance after transfer"),
                () -> assertEquals(TransactionType.TRANSFER, transactionIn.getType(), "The transaction type should be transfer"),
                () -> assertEquals(TransactionType.TRANSFER, transactionOut.getType(), "The transaction type should be transfer"),
                () -> assertEquals(500.0, transactionOut.getAmount(), "The transaction amount should equal with the actual amount after transfer"),
                () -> assertEquals(500.0, transactionIn.getAmount(), "The transaction amount should equal with the actual amount after transfer"),
                // Outgoing transaction
                () -> assertEquals(1, transactionOut.getFromAccountId()),
                () -> assertEquals(2, transactionOut.getToAccountId()),
                // Incoming transaction
                () -> assertEquals(1, transactionIn.getFromAccountId()),
                () -> assertEquals(2, transactionIn.getToAccountId())
        );
    }

    @Test
    void shouldNotTransferMoneyWhenInsufficientFunds() {
        bank.addAccount(account1);

        Account account2 = new Account(2, "Maria", 2000.0);
        bank.addAccount(account2);

        double account1StartingBalance = account1.getBalance();
        double account2StartingBalance = account2.getBalance();

        int account1TransactionsCount = account1.getTransactions().size();
        int account2TransactionsCount = account2.getTransactions().size();

        assertThrows(InsufficientFundsException.class, () -> bank.transfer(1, 2, 5000));

        assertAll(
                () -> assertEquals(account1StartingBalance, account1.getBalance(), "The balance should be the same after a failed transaction"),
                () -> assertEquals(account2StartingBalance, account2.getBalance(), "The balance should be the same after a failed transaction"),
                () -> assertEquals(account1TransactionsCount, account1.getTransactions().size(), "A failed transaction should not taken into account in transactions list."),
                () -> assertEquals(account2TransactionsCount, account2.getTransactions().size(), "A failed transaction should not taken into account in transactions list.")
        );
    }

    @Test
    void shouldThrowExceptionWhenSourceAccountDoesNotExist() {
        Account account2 = new Account(2, "Maria", 2000.0);
        bank.addAccount(account2);

        double account2StartingBalance = account2.getBalance();
        int account2TransactionsCount = account2.getTransactions().size();

        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> bank.transfer(1, 2, 100));

        assertAll(
                () -> assertEquals(account2TransactionsCount, account2.getTransactions().size(), "A failed transaction should not taken into account in transactions list."),
                () -> assertEquals(account2StartingBalance, account2.getBalance(), "The balance should be the same after a failed transaction"),
                () -> assertEquals("The account with id 1 could not be found", exception.getMessage())
        );
    }

    @Test
    void shouldThrowExceptionWhenDestinationAccountDoesNotExist() {
        bank.addAccount(account1);

        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> bank.transfer(1, 2, 100));

        assertEquals("The account with id 2 could not be found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTransferringToSameAccount() {
        bank.addAccount(account1);

        IllegalArgumentException  exception = assertThrows(IllegalArgumentException.class, () -> bank.transfer(1, 1, 100));

        assertEquals("Source and destination accounts must be different.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTransferringLeadsToOverflowAccount() {
        bank.addAccount(account1);
        Account account2 = new Account(2, "Maria", Double.MAX_VALUE);
        bank.addAccount(account2);

        assertThrows(IllegalArgumentException.class, () -> bank.transfer(1, 2, Double.MAX_VALUE));
        assertAll("Failed transaction state verification",
                () -> assertEquals(1000, account1.getBalance(), "The balance should be the same after a failed transaction"),
                () -> assertEquals(Double.MAX_VALUE, account2.getBalance(), "The balance should be the same after a failed transaction"),
                () -> assertEquals(0, account1.getTransactions().size(), "The transactions list should be empty."),
                () -> assertEquals(0, account2.getTransactions().size(), "The transactions list should be empty.")
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {-100.0, 0.0, Double.NaN, Double.POSITIVE_INFINITY})
    void shouldThrowExceptionWhenTransferAmountIsInvalid(double withdrawAmount) {
        bank.addAccount(account1);
        Account account2 = new Account(2, "Maria", 1000);
        bank.addAccount(account2);

        assertThrows(IllegalArgumentException.class, () -> bank.transfer(1, 2, withdrawAmount));

        assertAll("Failed transaction state verification",
                () -> assertEquals(1000, account1.getBalance(), "The balance should be the same after a failed transaction"),
                () -> assertEquals(1000, account2.getBalance(), "The balance should be the same after a failed transaction"),
                () -> assertEquals(0, account1.getTransactions().size(), "The transactions list should be empty."),
                () -> assertEquals(0, account2.getTransactions().size(), "The transactions list should be empty.")
        );
    }
}