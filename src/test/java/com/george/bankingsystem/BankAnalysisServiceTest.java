package com.george.bankingsystem;

import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;
import com.george.bankingsystem.service.BankAnalysisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankAnalysisServiceTest {
    private Bank bank;
    private BankAnalysisService bankAnalysisService;
    private Account account2;

    @BeforeEach
    void setUpEach() {
        this.bank = new Bank();
        Account account1 = new Account(1, "George", 2000.0);
        this.account2 = new Account(2, "Jane", 3000.0);
        bank.addAccount(account1);
        bank.addAccount(account2);
        this.bankAnalysisService = new BankAnalysisService(bank);
    }

    @Test
    void shouldReturnTotalBankBalance() {
        assertEquals(5000.0, bankAnalysisService.getTotalBankBalance());
    }

    @Test
    void shouldReturnAverageBalance() {
        assertEquals(2500.0, bankAnalysisService.getAverageBalance());
    }

    @Test
    void shouldReturnAccountsCount() {
        assertEquals(2, bankAnalysisService.getAccountsCount());
    }

    @Test
    void shouldReturnZeroForEmptyBank() {
        Bank emptyBank = new Bank();
        BankAnalysisService service = new BankAnalysisService(emptyBank);

        assertAll(
                () -> assertEquals(0.0, service.getTotalBankBalance()),
                () -> assertEquals(0.0, service.getAverageBalance()),
                () -> assertEquals(0, service.getAccountsCount())
        );
    }

    @Test
    void shouldReturnAccountWithHighestBalance() {
        assertEquals(3000.0, bankAnalysisService.getHighestBalance());
    }

    @Test
    void shouldReturnAccountWithLowestBalance() {
        assertEquals(2000.0, bankAnalysisService.getLowestBalance());
    }

    @Test
    void shouldReturnAccountsWithPositiveBalance() {
        Account account3 = new Account(3, "Bill", 0.0);
        bank.addAccount(account3);

        assertEquals(2, bankAnalysisService.getAccountsWithPositiveBalance());
    }

    @Test
    void shouldReturnRichestAccount() {
        assertEquals(account2, bankAnalysisService.getRichestAccount());
    }

    @Test
    void shouldThrowExceptionForEmptyBank() {
        Bank emptyBank = new Bank();
        BankAnalysisService service = new BankAnalysisService(emptyBank);

        assertAll(
                () -> assertThrows(IllegalStateException.class, service::getRichestAccount),
                () -> assertEquals(0.0, service.getHighestBalance()),
                () -> assertEquals(0.0, service.getLowestBalance()),
                () -> assertEquals(0, service.getAccountsWithPositiveBalance())
        );
    }
}
