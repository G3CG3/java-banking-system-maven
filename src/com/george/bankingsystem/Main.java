import com.george.bankingsystem.exceptions.*;
import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;
import com.george.bankingsystem.service.*;

void main() {

    // Valid Account
    Account acc1 = new Account(1, "George", 1000.0);

    // Invalid Account due to AccountiD
    try {
        Account acc2 = new Account(-3, "John", 2000.0);
    } catch (InvalidAccountIdException e) {
        System.err.println(e.getMessage());
    }

    // Invalid Account due to negative balance
    try {
        Account acc3 = new Account(3, "Nick", -3000.0);
    } catch (NegativeInitialBalanceException e) {
        System.err.println(e.getMessage());
    }

    // Invalid Account due to not having an owner name
    try {
        Account acc4 = new Account(4, "", -3000.0);
    } catch (InvalidOwnerNameException e) {
        System.err.println(e.getMessage());
    }

    // Valid deposit
    acc1.deposit(500);
    System.out.println(acc1.getBalance()); // Should be 1500.

    // Invalid deposit due to negative sum
    try {
        acc1.deposit(-500);
    } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
    }

    // Invalid deposit due to zero sum (Edge Case)
    try {
        acc1.deposit(0);
    } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
    }

    // Valid withdrawal
    acc1.withdraw(500);
    System.out.println(acc1.getBalance()); // Should be 1000.

    // Invalid withdrawal due to insufficient funds
    try {
        acc1.withdraw(2500);
    }  catch (InsufficientFundsException e) {
        System.err.println(e.getMessage());
    }

    // Invalid withdrawal due to negative number
    try {
        acc1.withdraw(-500);
    }  catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
    }

    // Invalid withdrawal due to zero sum (Edge Case)
    try {
        acc1.withdraw(0);
    } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
    }

    // Bank creation
    Bank bank = new Bank();

    // Valid account addition
    bank.addAccount(acc1);

    // Duplicate Account Case
    try {
        bank.addAccount(acc1);
    } catch (DuplicateAccountException e) {
        System.err.println(e.getMessage());
    }

    // Account addition for transfer purposes
    Account acc5 = new Account(5, "John", 2000.0);
    bank.addAccount(acc5);

    System.out.println(bank.getAccounts()); // Should show 2 accounts.

    // Valid transfer
    bank.transfer(1, 5, 500);
    System.out.println(acc1.getBalance()); // Should be 500.
    System.out.println(acc5.getBalance()); // Should be 2500.

    // Self transfer case
    try {
        bank.transfer(1, 1, 100);
    } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
    }

    // Invalid transfer amount case
    try {
        bank.transfer(1, 5, 5000);
    } catch (InsufficientFundsException e) {
        System.err.println(e.getMessage());
    }

    // Account not found
    try {
        bank.transfer(1, 15, 100);
    } catch (AccountNotFoundException e) {
        System.err.println(e.getMessage());
    }
}