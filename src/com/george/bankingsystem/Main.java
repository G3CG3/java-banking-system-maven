import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;

void main() {
    Account acc1 = new Account(1, "George", 350.0);

    Bank bank = new Bank();
    bank.addAccount(acc1);

    acc1.deposit(100); // 450.0
    acc1.deposit(200.0); //650.0
    acc1.withdraw(50.0); //600.0
    acc1.deposit(300.0); //900.0
    acc1.withdraw(25.0); //875.0

    System.out.println("Total deposits: " + acc1.getTotalDeposits());
    System.out.println("Total withdrawals: " + acc1.getTotalWithdrawals());
    System.out.println("Transaction count: " + acc1.getTransactionCount());
    System.out.println("Average transaction amount: " + acc1.getAverageTransactionAmount());

}