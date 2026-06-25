import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;

void main() {
    Account acc1 = new Account(1, "George", 350.0);
    Account acc2 = new Account(2, "Maria", 1000.0);

    Bank bank = new Bank();
    bank.addAccount(acc1);
    bank.addAccount(acc2);

    bank.transfer(2, 1, 150.0);
    System.out.println(acc1.getBalance());
    System.out.println(acc2.getBalance());
}