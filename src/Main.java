import com.george.bankingsystem.exceptions.InsufficientFundsException;
import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;

void main() {
    Account acc1 = new Account(1, "George", 350.0);
    Account acc2 = new Account(1, "Maria", 1000.0);
    Account acc3 = new Account(3, "John", 200.0);

    Bank bank = new Bank();
    bank.addAccount(acc1);
    bank.addAccount(acc2);
    bank.addAccount(acc3);

    bank.transfer(1, 2, 150.0); // acc1 = 200, acc2 = 1150
    System.out.println(acc1.getBalance());
    System.out.println(acc2.getBalance());
    System.out.println(acc3.getBalance());
}
