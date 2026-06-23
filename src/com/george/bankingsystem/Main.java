import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;

void main() {
    Account acc1 = new Account(1, "George", 350.0);

    Bank bank = new Bank();
    bank.addAccount(acc1);

    acc1.deposit(100); // 450.0
    acc1.withdraw(150.0); // 300.0
    acc1.deposit(200.0); //500.0

    System.out.println(acc1.getTransactions());
}
