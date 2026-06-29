import com.george.bankingsystem.exceptions.InvalidOwnerNameException;
import com.george.bankingsystem.exceptions.NegativeInitialBalanceException;
import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;

void main() {
    try {
        Account acc1 = new Account(1, " ", 100.0);
    } catch (InvalidOwnerNameException e) {
        System.out.println(e.getMessage());
    }

    try {
        Account acc2 = new Account(2, "George", -100.0);
    } catch (NegativeInitialBalanceException e) {
        System.out.println(e.getMessage());
    }

    Account acc3 = new Account(3, "george", 1000.0);
    Account acc4 = new Account(4, "Nick", 500.0);
    Account acc5 = new Account(5, "Zachary", 100.0);
    Account acc6 = new Account(6, "Helen", 1000.0);
    Account acc7 = new Account(7, "Maria", 400.0);
    Account acc8 = new Account(8, "Brodie", 650.0);
    Account acc9 = new Account(9, "Vance", 4000.0);

    Bank bank = new Bank();
    //bank.addAccount(acc2);
    //bank.addAccount(acc3);
    bank.addAccount(acc4);
    bank.addAccount(acc5);
    bank.addAccount(acc6);
    bank.addAccount(acc7);
    bank.addAccount(acc8);
    bank.addAccount(acc9);
    //bank.addAccount(acc10);

    //acc1.deposit(500);
    //acc2.deposit(400);
    acc3.deposit(750);
    //acc1.withdraw(250);
    //acc1.withdraw(50);
    //acc2.withdraw(500);
    bank.transfer(6, 2, 500);

    System.out.println("===== BANK DASHBOARD =====");
    System.out.println();
    System.out.println("Accounts: " + bank.getAccountsCount());
    System.out.println();
    System.out.println("Positive balances: " + bank.getAccountsWithPositiveBalance());
    System.out.println();
    System.out.println("Total balance: " + bank.getTotalBankBalance());
    System.out.println();
    System.out.println("Average balance: " + bank.getAverageBalance());
    System.out.println();
    System.out.println("Highest balance: " + bank.getHighestBalance());
    System.out.println();
    System.out.println("Lowest balance: " + bank.getLowestBalance());
    System.out.println();
    System.out.println("Richest account: " + bank.getRichestAccount());
    System.out.println();
    System.out.println("==========================");
}