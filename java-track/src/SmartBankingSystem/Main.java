package SmartBankingSystem;

import java.util.List;
import java.util.Scanner;

import SmartBankingSystem.controller.BankingController;
import SmartBankingSystem.entity.Account;
import SmartBankingSystem.entity.Customer;
import SmartBankingSystem.entity.Transaction;
import SmartBankingSystem.exception.BankingException;
import SmartBankingSystem.service.SmartBankingSystemImpl;
import SmartBankingSystem.util.BankingConstants;
import SmartBankingSystem.util.CurrencyFormatter;

/**
 * Console user interface for the Smart Banking System.
 * <p>
 * This class only reads text and prints results. All input validation happens
 * in {@link BankingController} and all business rules in the service layer;
 * any {@link BankingException} they raise is shown to the user as an error message.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final BankingController controller = new BankingController(new SmartBankingSystemImpl());

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = prompt("Enter choice");

            try {
                switch (choice) {
                    case "1" -> createCustomer();
                    case "2" -> openAccount();
                    case "3" -> makeInitialDeposit();
                    case "4" -> depositMoney();
                    case "5" -> withdrawMoney();
                    case "6" -> transferMoney();
                    case "7" -> checkBalance();
                    case "8" -> viewTransactionHistory();
                    case "9" -> viewAllCustomers();
                    case "10" -> {
                        System.out.println("Thank you for using Smart Banking System. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please select 1-10.");
                }
            } catch (BankingException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n========== SMART BANKING SYSTEM ==========");
        System.out.println("1. Create Customer");
        System.out.println("2. Open Account");
        System.out.println("3. Make Initial Deposit (activate account)");
        System.out.println("4. Deposit Money");
        System.out.println("5. Withdraw Money");
        System.out.println("6. Transfer Money");
        System.out.println("7. Check Balance");
        System.out.println("8. View Transaction History");
        System.out.println("9. View All Customers");
        System.out.println("10. Exit");
    }

    /**
     * Prints a label and reads one line of input.
     * Exits cleanly if the input stream is closed (e.g. Ctrl+D or end of a piped file).
     */
    private static String prompt(String label) {
        System.out.print(label + ": ");
        if (!scanner.hasNextLine()) {
            System.out.println("\nInput closed. Goodbye!");
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    private static void createCustomer() {
        String name = prompt("Enter Customer Name");
        String email = prompt("Enter Email");

        Customer customer = controller.createCustomer(name, email);
        System.out.println("Customer created successfully! Customer ID: " + customer.getCustomerId());
    }

    private static void openAccount() {
        String customerId = prompt("Enter Customer ID");

        Account account = controller.openAccount(customerId);
        System.out.println("Account opened! Account Number: " + account.getAccountNumber()
                + " | Status: " + account.getStatus());
        System.out.println("Make an initial deposit of at least "
                + CurrencyFormatter.format(BankingConstants.MINIMUM_BALANCE) + " (option 3) to activate it.");
    }

    private static void makeInitialDeposit() {
        String accountNumber = prompt("Enter Account Number");
        String amount = prompt("Enter Initial Deposit (min "
                + CurrencyFormatter.format(BankingConstants.MINIMUM_BALANCE) + ")");

        Transaction txn = controller.makeInitialDeposit(accountNumber, amount);
        printReceipt("Initial deposit successful! Account is now ACTIVE.", txn, txn.getToAccount());
    }

    private static void depositMoney() {
        String accountNumber = prompt("Enter Account Number");
        String amount = prompt("Enter Amount to Deposit");

        Transaction txn = controller.deposit(accountNumber, amount);
        printReceipt("Deposit successful!", txn, txn.getToAccount());
    }

    private static void withdrawMoney() {
        String accountNumber = prompt("Enter Account Number");
        String amount = prompt("Enter Amount to Withdraw");

        Transaction txn = controller.withdraw(accountNumber, amount);
        printReceipt("Withdrawal successful!", txn, txn.getFromAccount());
    }

    private static void transferMoney() {
        String source = prompt("Enter Source Account");
        String destination = prompt("Enter Destination Account");
        String amount = prompt("Enter Amount");

        Transaction txn = controller.transfer(source, destination, amount);
        printReceipt("Transfer successful!", txn, txn.getFromAccount());
    }

    private static void checkBalance() {
        Account account = controller.getAccount(prompt("Enter Account Number"));
        System.out.printf("Account %s | Status: %s | Balance: %s%n",
                account.getAccountNumber(), account.getStatus(), CurrencyFormatter.format(account.getBalance()));
    }

    private static void viewTransactionHistory() {
        List<Transaction> transactions = controller.getTransactionHistory(prompt("Enter Account Number"));
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this account.");
            return;
        }

        System.out.println("\n--- Transaction History ---");
        transactions.forEach(System.out::println);
    }

    private static void viewAllCustomers() {
        List<Customer> customers = controller.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        System.out.println("\n--- Customer List ---");
        customers.forEach(System.out::println);
    }

    /** Shared success output: message, transaction ID, amount and the account's new balance. */
    private static void printReceipt(String message, Transaction txn, String balanceAccountNumber) {
        Account account = controller.getAccount(balanceAccountNumber);
        System.out.printf("%s Transaction ID: %s | Amount: %s | Balance of %s: %s%n",
                message, txn.getTransactionId(), CurrencyFormatter.format(txn.getAmount()),
                account.getAccountNumber(), CurrencyFormatter.format(account.getBalance()));
    }
}
