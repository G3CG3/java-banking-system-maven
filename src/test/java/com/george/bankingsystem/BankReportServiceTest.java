package com.george.bankingsystem;

import com.george.bankingsystem.model.Bank;
import com.george.bankingsystem.service.BankReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankReportServiceTest {
    private BankReportService bankReportService;

    @BeforeEach
    void setup() {
        Bank bank = new Bank();
        this.bankReportService = new BankReportService(bank);
    }

    @Test
    void shouldReturnMessageForEmptyBank() {
        assertEquals("No accounts available.", bankReportService.generateOwnerReport());
    }
}
