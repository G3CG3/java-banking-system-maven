import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;

void main() {
    Account acc1 = new Account(1, "George", 350.0);
    Account acc2 = new Account(2, "Maria", 900.0);
    Account acc3 = new Account(3, "george", 1000.0);
    Account acc4 = new Account(4, "Nick", 500.0);
    Account acc5 = new Account(5, "Zachary", 100.0);
    Account acc6 = new Account(6, "Helen", 1000.0);
    Account acc7 = new Account(7, "Maria", 400.0);
    Account acc8 = new Account(8, "Brodie", 650.0);
    Account acc9 = new Account(9, "Vance", 4000.0);
    Account acc10 = new Account(10, "Thomas", 50.0);

    Bank bank = new Bank();
    bank.addAccount(acc1);
    bank.addAccount(acc2);
    bank.addAccount(acc3);
    bank.addAccount(acc4);
    bank.addAccount(acc5);
    bank.addAccount(acc6);
    bank.addAccount(acc7);
    bank.addAccount(acc8);
    bank.addAccount(acc9);
    bank.addAccount(acc10);

    acc1.deposit(500);
    acc2.deposit(400);
    acc3.deposit(750);
    acc1.withdraw(250);
    acc1.withdraw(50);
    acc2.withdraw(500);
    bank.transfer(6, 2, 500);

    System.out.println("Accounts sorted by balance: ");
    System.out.println();
    System.out.println(bank.getAccountsSortedByBalance());
    System.out.println("Accounts sorted by owner name: ");
    System.out.println();
    System.out.println(bank.getAccountsSortedByOwnerName());
    System.out.println("Transactions sorted by amount: ");
    System.out.println();
    System.out.println(acc1.getTransactionsSortedByAmount());
    System.out.println("Transactions sorted by date: ");
    System.out.println();
    System.out.println(acc1.getTransactionsSortedByDate());
}