import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;
import com.george.bankingsystem.model.Transaction;

void main() {
    Account acc1 = new Account(1, "George", 350.0);
    Account acc2 = new Account(2, "Maria", 900.0);
    Account acc3 = new Account(3, "george", 1000.0);

    Bank bank = new Bank();
    bank.addAccount(acc1);
    bank.addAccount(acc2);
    bank.addAccount(acc3);

    System.out.println(bank.findAccountsByOwner("George"));
    System.out.println(bank.findAccountsByOwner("george"));

    System.out.println(bank.getTotalBankBalance()); // It should return 2250.0.

    System.out.println(bank.getRichestAccount()); // It should return acc3.

    System.out.println(bank.getAverageBalance()); // It should return 750.0.
}