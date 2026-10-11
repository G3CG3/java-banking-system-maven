package com.george.bankingsystem;

import com.george.bankingsystem.exceptions.AccountNotFoundException;
import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;
import com.george.bankingsystem.service.AccountSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AccountSearchServiceTest {
    private Bank bank;
    private AccountSearchService accountSearchService;
    private Account account1;
    private Account account2;

    @BeforeEach
    void setUp() {
        this.bank = new Bank();
        this.account1 = new Account(1, "George", 1000.0);
        this.account2 = new Account(2, "Maria", 2000.0);
        bank.addAccount(account1);
        bank.addAccount(account2);
        this.accountSearchService = new AccountSearchService(bank);
    }

    @Test
    void shouldFindAccountById() {
        Account account = accountSearchService.findAccountById(2);

        assertSame(account2, account);
    }

    @Test
    void shouldThrowAccountNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(AccountNotFoundException.class, () -> accountSearchService.findAccountById(3));
    }

    @Test
    void shouldFindAccountByOwnerIgnoreCase() {
        assertAll(
                () -> assertEquals(List.of(account2), accountSearchService.findAccountsByOwner("Maria")),
                () -> assertEquals(List.of(account2), accountSearchService.findAccountsByOwner("maria"))
        );
    }

    @Test
    void shouldReturnAccountWithBalanceAboveCertainAmount() {
        assertEquals(List.of(account2), accountSearchService.findAccountsWithBalanceAbove(1500));
    }

    @Test
    void shouldReturnAccountByOwnerNameIgnoreCaseAndAboveCertainAmount() {
        assertEquals(List.of(account2), accountSearchService.findAccountsByOwnerWithBalanceAbove("maria", 1500));
    }

    @Test
    void shouldReturnAccountsGroupedByOwnerName() {
        Map<String, List<Account>> map = accountSearchService.groupAccountsByOwner();

        assertAll("Grouped Map Assertions",
                () -> assertTrue(map.containsKey("George"), "Map should contain key 'George'"),
                () -> assertTrue(map.containsKey("Maria"), "Map should contain key 'Maria'"),
                () -> assertEquals(List.of(account1), map.get("George"), "Account list for George should match"),
                () -> assertEquals(List.of(account2), map.get("Maria"), "Account list for Maria should match"),
                () -> assertEquals(2, map.size(), "Map size should be 2")
        );
    }

    @Test
    void shouldPartitionAccountsByPositiveBalance() {
        Account account3 = new Account(3, "Athena", 0.0);
        bank.addAccount(account3);

        Map<Boolean, List<Account>> partitioned = accountSearchService.partitionAccountsByPositiveBalance();

        assertAll("Partition accounts by positive balance (> 0)",
                () -> assertTrue(partitioned.containsKey(true), "Map should contain key true"),
                () -> assertTrue(partitioned.containsKey(false), "Map should contain key false"),
                () -> assertEquals(List.of(account1, account2), partitioned.get(true), "Positive balance accounts should match"),
                () -> assertEquals(List.of(account3), partitioned.get(false), "Non-positive balance accounts should match"),
                () -> assertEquals(2, partitioned.size(), "Map should have exactly 2 keys")
        );
    }

    @Test
    void shouldRejectNullOrBlankOwnerName() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> accountSearchService.findAccountsByOwner(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> accountSearchService.findAccountsByOwnerWithBalanceAbove(null, 1500)),
                () -> assertThrows(IllegalArgumentException.class, () -> accountSearchService.findAccountsByOwner("")),
                () -> assertThrows(IllegalArgumentException.class, () -> accountSearchService.findAccountsByOwnerWithBalanceAbove("", 1500)),
                () -> assertThrows(IllegalArgumentException.class, () -> accountSearchService.findAccountsByOwner(" ")),
                () -> assertThrows(IllegalArgumentException.class, () -> accountSearchService.findAccountsByOwnerWithBalanceAbove(" ", 1500))
        );
    }

    @Test
    void shouldReturnEmptyListWhenOwnerNameIsUnknown() {
        assertEquals(List.of(), accountSearchService.findAccountsByOwner("Unknown"));
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoAccountsAboveABalance() {
        assertEquals(List.of(), accountSearchService.findAccountsWithBalanceAbove(3000));
    }
}