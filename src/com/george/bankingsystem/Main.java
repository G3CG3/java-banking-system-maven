import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;
import com.george.bankingsystem.model.Transaction;

void main() {
    Account acc1 = new Account(1, "George", 350.0);
    Account acc2 = new Account(2, "Maria", 1000.0);

    Bank bank = new Bank();
    bank.addAccount(acc1);
    bank.addAccount(acc2);

    bank.transfer(2, 1, 150.0);
    acc1.deposit(500.0);
    acc1.withdraw(200.0);

    System.out.println(acc1.getTransactionCount());

    List<Transaction> list = acc1.getTransactions();

    try {
        list.clear();
    } catch (UnsupportedOperationException e) {
        System.out.println("Cannot modify transaction history.");
    }

    System.out.println(acc1.getTransactionCount());
}