import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;

void main() {
    Account acc1 = new Account(1, "George", 350.0);
    Account acc2 = new Account(2, "Maria", 1000.0);

    Bank bank = new Bank();
    bank.addAccount(acc1);
    bank.addAccount(acc2);

    bank.transfer(2, 1, 150.0);
    acc1.deposit(500.0);
    acc1.withdraw(200.0);
    System.out.println(acc1.getDeposits());
    System.out.println(acc1.getWithdrawals());
    System.out.println(acc1.getTransfers());
    System.out.println(acc1.getTransactionsAbove(300.0));
}