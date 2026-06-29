import com.george.bankingsystem.model.Account;
import com.george.bankingsystem.model.Bank;
import com.george.bankingsystem.service.AccountSearchService;

void main() {
    Account acc3 = new Account(3, "George", 1000.0);
    Account acc4 = new Account(4, "Nick", 500.0);
    Account acc5 = new Account(5, "Zachary", 100.0);
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

    bank.transfer(6, 8, 500);

    AccountSearchService accountSearchService = new AccountSearchService(bank);
    System.out.println("The accounts of the owner named George:");
    System.out.println(accountSearchService.findAccountsByOwner("George"));
    System.out.println("The accounts of the owner named George and balance above 500:");
    System.out.println(accountSearchService.findAccountsByOwnerWithBalanceAbove("George", 500));
    System.out.println("The transactions of account #3 above 250 and under 750");
    System.out.println(acc3.getTransactionsBetween(100, 750));
    System.out.println("Accounts with balance above 900:");
    System.out.println(accountSearchService.findAccountsWithBalanceAbove(900));
}