package com.george.bankingsystem;

import com.george.bankingsystem.exceptions.InsufficientFundsException;
import com.george.bankingsystem.exceptions.InvalidAccountIdException;
import com.george.bankingsystem.exceptions.InvalidOwnerNameException;
import com.george.bankingsystem.exceptions.NegativeInitialBalanceException;
import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;
import com.george.bankingsystem.model.Transaction;
import com.george.bankingsystem.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    @ValueSource(doubles = {-100.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
    void shouldThrowExceptionAtAccountCreationWithInvalidBalance(double balance) {
        assertThrows(NegativeInitialBalanceException.class, () -> new Account(2, "Marian", balance));
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
                () -> assertEquals(amount, transaction.getAmount(), "Incorrect transaction value"),
                () -> assertEquals(TransactionType.DEPOSIT, transaction.getType()),
                () -> assertEquals(expectedBalance, account.getBalance(), "Incorrect balance value")
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {-100.0, -1.0, -0.01, 0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
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
                () -> assertEquals(1000.0, account.getBalance(), "Incorrect balance value")
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {-100.0, -1.0, -0.01, 0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
    void shouldThrowExceptionWhenWithdrawAmountIsInvalid(double amount) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> account.withdraw(amount));

        assertEquals("The withdrawing amount should be a positive number", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenWithdrawAmountIsLargerThanBalance() {
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(2000.0));
        assertEquals(1000.0, account.getBalance(), "The balance should remain unchanged");
        assertEquals(0, account.getTransactions().size(), "Failed transactions should not be taken into account");
    }

    @Test
    void shouldThrowExceptionWhenDepositExceedsFiniteAmount() {
        Account account1 = new Account(2, "Maria", Double.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> account1.deposit(Double.MAX_VALUE));
        assertEquals(Double.MAX_VALUE, account1.getBalance(), "The balance should remain unchanged");
        assertEquals(0, account1.getTransactions().size(), "Failed transactions should not be taken into account");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void shouldThrowExceptionWhenIdIsNotAPositiveNumber(int id) {
        assertThrows(InvalidAccountIdException.class, () -> new Account(id, "Maria", 1000.0));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void shouldThrowExceptionWhenOwnerNameIsNullOrBlank(String name) {
        assertThrows(InvalidOwnerNameException.class,
                () -> new Account(1, name, 1000.0));
    }

    @Test
    void shouldMakeAValidWithdrawal() {
        account.withdraw(500.0);

        assertAll(
                () -> assertEquals(500.0, account.getBalance()),
                () -> assertEquals(500.0, account.getTransactions().getFirst().getAmount()),
                () -> assertEquals(1, account.getTransactions().size(), "Failed transactions should not be taken into account"),
                () -> assertEquals(TransactionType.WITHDRAW, account.getTransactions().getFirst().getType())
        );
    }

    @Test
    void shouldThrowExceptionWhenTryingToModifyOriginalList() {
        account.deposit(500.0);
        List<Transaction> transactions = account.getTransactions();

        assertThrows(UnsupportedOperationException.class, () -> transactions.add(new Transaction(TransactionType.DEPOSIT, 500.0)));
    }

    @Test
    void shouldReturnDeposits() {
        account.deposit(500.0);
        account.withdraw(1000.0);
        account.deposit(250.0);

        List<Transaction> deposits = account.getDeposits();

        assertThat(deposits)
                .hasSize(2)
                .extracting(Transaction::getType)
                .allMatch(type -> type == TransactionType.DEPOSIT);

        assertThat(deposits)
                .extracting(Transaction::getAmount)
                .containsExactly(500.0, 250.0);
    }

    @Test
    void shouldReturnWithdrawals() {
        account.deposit(500.0);
        account.withdraw(1000.0);
        account.deposit(250.0);
        account.withdraw(100.0);

        List<Transaction> withdrawals = account.getWithdrawals();

        assertThat(withdrawals)
                .hasSize(2)
                .extracting(Transaction::getType)
                .allMatch(type -> type == TransactionType.WITHDRAW);

        assertThat(withdrawals)
                .extracting(Transaction::getAmount)
                .containsExactly(1000.0, 100.0);
    }

    @Test
    void shouldReturnTransfers() {
        Account account1 = new Account(2, "Maria", 2000.0);
        Bank bank = new Bank();

        bank.addAccount(account);
        bank.addAccount(account1);

        account.deposit(1500.0);
        bank.transfer(1, 2, 500.0);
        bank.transfer(1, 2, 750.0);

        List<Transaction> transfers = account.getTransfers();

        assertThat(transfers)
                .hasSize(2)
                .extracting(Transaction::getType)
                .allMatch(type -> type == TransactionType.TRANSFER);

        assertThat(transfers)
                .extracting(Transaction::getAmount)
                .containsExactly(500.0, 750.0);
    }

    @Test
    void shouldReturnOnlyTransactionsAboveThreshold() {
        account.deposit(500.0);
        account.withdraw(1000.0);
        account.deposit(250.0);
        account.withdraw(100.0);

        assertThat(account.getTransactionsAbove(250.0))
                .hasSize(2)
                .extracting(Transaction::getAmount)
                .containsExactly(500.0, 1000.0);
    }

    @Test
    void shouldReturnOnlyTransactionsBetweenMinMax() {
        account.deposit(500.0);
        account.withdraw(1000.0);
        account.deposit(250.0);
        account.withdraw(100.0);

        assertThat(account.getTransactionsBetween(250.0, 500.0))
                .hasSize(2)
                .extracting(Transaction::getAmount)
                .containsExactly(500.0, 250.0);
    }

    @Test
    void shouldDetectLargeTransactions() {
        account.deposit(500.0);
        account.deposit(250.0);
        account.withdraw(100.0);

        assertThat(account.hasLargeTransaction(500.0)).isTrue();
        assertThat(account.hasLargeTransaction(250.0)).isTrue();
        assertThat(account.hasLargeTransaction(1000.0)).isFalse();
    }

    @Test
    void shouldDetectIfTheAccountHasDepositsOnly() {
        Account account1 = new Account(2, "Maria", 2000.0);
        account.deposit(500.0);
        account1.withdraw(1000.0);
        account1.deposit(250.0);

        assertThat(account.hasOnlyDeposits()).isTrue();
        assertThat(account1.hasOnlyDeposits()).isFalse();
    }

    @Test
    void shouldDetectIfThereIsATransactionInTheAccount() {
        assertThat(account.hasNoTransactions()).isTrue();
        assertThat(account.hasTransactions()).isFalse();

        account.deposit(500.0);

        assertThat(account.hasNoTransactions()).isFalse();
        assertThat(account.hasTransactions()).isTrue();
    }
}
