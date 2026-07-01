import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;
import com.george.bankingsystem.service.AccountSearchService;

void main() {
    Account acc3 = new Account(3, "George", 1000.0);
    Account acc4 = new Account(4, "Nick", 500.0);
    Account acc5 = new Account(5, "George", 100.0);
    Account acc6 = new Account(6, "Helen", 1000.0);
    Account acc7 = new Account(7, "Maria", 400.0);
    Account acc8 = new Account(8, "Brodie", 650.0);
    Account acc9 = new Account(9, "Vance", 4000.0);

    Bank bank = new Bank();
    bank.addAccount(acc3);
    bank.addAccount(acc4);
    bank.addAccount(acc5);
    bank.addAccount(acc6);
    bank.addAccount(acc7);
    bank.addAccount(acc8);
    bank.addAccount(acc9);

    acc3.deposit(750);
    acc3.deposit(250);
    acc3.withdraw(250);
    acc3.withdraw(100);

    acc4.deposit(750);

    bank.transfer(6, 8, 500);

    AccountSearchService accountSearchService = new AccountSearchService(bank);
    System.out.println("Accounts separated with positive and non positive balance:");
    System.out.println(accountSearchService.partitionAccountsByPositiveBalance());
    System.out.println();

    System.out.println("Transactions exceeding the specified amount:");
    System.out.println(acc3.hasLargeTransaction(500));
    System.out.println();

    System.out.println("Are all the transactions of this account deposits?");
    System.out.println(acc3.hasOnlyDeposits());

    System.out.println("Does this account have no transactions?");
    System.out.println(acc9.hasNoTransactions());
    System.out.println(acc3.hasNoTransactions());
}