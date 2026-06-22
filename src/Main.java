import com.george.bankingsystem.exceptions.InsufficientFundsException;
import com.george.bankingsystem.model.Account;

void main() {
    Account acc1 = new Account(1, "George", 350.0);

    acc1.deposit(50); //400

    acc1.withdraw(200); //200

    System.out.println(acc1);
    System.out.println();

    try {
        System.out.println("You account balance is: " + acc1.getBalance());
        acc1.withdraw(500);
    } catch (InsufficientFundsException e) {
        System.out.println(e.getMessage());
    }
    System.out.println("You account balance is: " + acc1.getBalance());
}
