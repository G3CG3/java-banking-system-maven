package com.george.bankingsystem;

import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;
import com.george.bankingsystem.service.AccountSortingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

public class AccountSortingServiceTest {
    private Account account1;
    private Account account2;
    private Account account3;
    private AccountSortingService accountSortingService;

    @BeforeEach
    void setUp() {
        Bank bank = new Bank();
        this.account1 = new Account(1, "george", 1000.0);
        this.account2 = new Account(2, "John", 2000.0);
        this.account3 = new Account(3, "Jane", 3000.0);
        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.addAccount(account3);
        this.accountSortingService = new AccountSortingService(bank);
    }

    @Test
    void shouldReturnAccountsInDescendingOrder() {
        List<Account> sortedAccounts = accountSortingService.getAccountsSortedByBalance();

        assertThat(sortedAccounts)
                .hasSize(3)
                .containsExactly(account3, account2, account1);
    }

    @Test
    void shouldReturnAccountsSortedByNameCaseInsensitive() {
        List<Account> sortedAccounts = accountSortingService.getAccountsSortedByOwnerName();

        assertThat(sortedAccounts)
                .extracting(Account::getOwnerName)
                .containsExactly("george", "Jane", "John");
    }

    @Test
    void shouldReturnEmptyListsWhenBankHasNoAccounts() {
        Bank bank = new Bank();
        AccountSortingService accountSortingService = new AccountSortingService(bank);

        List<Account> sortedAccounts = accountSortingService.getAccountsSortedByBalance();
        List<Account> sortedAccountsByOwnerName = accountSortingService.getAccountsSortedByOwnerName();

        assertAll(
                () -> assertThat(sortedAccounts).isEmpty(),
                () -> assertThat(sortedAccountsByOwnerName).isEmpty()

        );
    }
}
