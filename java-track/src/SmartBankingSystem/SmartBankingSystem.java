package SmartBankingSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SmartBankingSystem {
    private static Map<Integer, Customer> customers = new LinkedHashMap<>();
    private static Map<Integer, Account> accounts = new HashMap<>();
    private static List<Transaction> transactions = new ArrayList<>();

    private static int customerIdCounter = 0;
    private static int accountNumberCounter = 0;
    private static int txnIdCounter = 0;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n========== SMART BANKING SYSTEM ==========");
            System.out.println("1. Create Customer");
            System.out.println("2. Create Account");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Transfer Money");
            System.out.println("6. Check Balance");
            System.out.println("7. View Transaction History");
            System.out.println("8. View All Customers");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 9.");
                continue;
            }

            switch (choice) {
                case 1: createCustomer(); break;
                case 2: createAccount(); break;
                case 3: depositMoney(); break;
                case 4: withdrawMoney(); break;
                case 5: transferMoney(); break;
                case 6: checkBalance(); break;
                case 7: viewTransactionHistory(); break;
                case 8: viewAllCustomers(); break;
                case 9:
                    System.out.println("Thank you for using Smart Banking System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Please select 1-9.");
            }
        }
    }

    private static void viewAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        System.out.println("\n--- Customer List ---");
        for (Customer c : customers.values()) {
            System.out.println(c);
        }
    }

    private static void createCustomer() {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        int id = customerIdCounter++;
        customers.put(id, new Customer(id, name, email));
        System.out.println("Customer Created Successfully! Customer ID: " + id);
    }

    private static void createAccount() {
        System.out.print("Enter Customer ID: ");
        int custId = Integer.parseInt(scanner.nextLine());
        if (!customers.containsKey(custId)) {
            System.out.println("Error: Customer ID not found!");
            return;
        }

        System.out.print("Enter Initial Deposit (INR): ");
        double initialDeposit = Double.parseDouble(scanner.nextLine());
        if (initialDeposit < 0) {
            System.out.println("Error: Initial deposit cannot be negative.");
            return;
        }

        int accNo = accountNumberCounter++;
        accounts.put(accNo, new Account(accNo, custId, initialDeposit));
        System.out.println("Account Created Successfully! Account Number: " + accNo);
    }

    private static void depositMoney() {
        System.out.print("Enter Account Number: ");
        int accNo = Integer.parseInt(scanner.nextLine());
        Account acc = accounts.get(accNo);

        if (acc == null) {
            System.out.println("Error: Account not found!");
            return;
        }

        System.out.print("Enter Amount to Deposit: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount <= 0) {
            System.out.println("Error: Amount must be greater than zero.");
            return;
        }

        acc.deposit(amount);
        String txnId = "TXN" + txnIdCounter++;
        transactions.add(new Transaction(txnId, "DEPOSIT", 0, accNo, amount, "SUCCESS"));
        System.out.printf("Deposit Successful! Transaction ID: %s | New Balance: INR %.2f%n", txnId, acc.getBalance());
    }

    private static void withdrawMoney() {
        System.out.print("Enter Account Number: ");
        int accNo = Integer.parseInt(scanner.nextLine());
        Account acc = accounts.get(accNo);

        if (acc == null) {
            System.out.println("Error: Account not found!");
            return;
        }

        System.out.print("Enter Amount to Withdraw: ");
        double amount = Double.parseDouble(scanner.nextLine());
        String txnId = "TXN" + txnIdCounter++;

        if (acc.withdraw(amount)) {
            transactions.add(new Transaction(txnId, "WITHDRAW", accNo, 0, amount, "SUCCESS"));
            System.out.printf("Withdrawal Successful! Transaction ID: %s | New Balance: INR %.2f%n", txnId, acc.getBalance());
        } else {
            transactions.add(new Transaction(txnId, "WITHDRAW", accNo, 0, amount, "FAILED"));
            System.out.println("Withdrawal Failed! Insufficient balance or invalid amount.");
        }
    }

    private static void transferMoney() {
        System.out.print("Enter Source Account: ");
        int srcAccNo = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Destination Account: ");
        int destAccNo = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        Account srcAcc = accounts.get(srcAccNo);
        Account destAcc = accounts.get(destAccNo);
        String txnId = "TXN" + txnIdCounter++;

        if (srcAcc == null || destAcc == null) {
            System.out.println("Transfer Failed! One or both accounts do not exist.");
            return;
        }

        if (srcAccNo == destAccNo) {
            System.out.println("Transfer Failed! Source and destination accounts cannot be the same.");
            return;
        }

        if (srcAcc.withdraw(amount)) {
            destAcc.deposit(amount);
            transactions.add(new Transaction(txnId, "TRANSFER", srcAccNo, destAccNo, amount, "SUCCESS"));

            System.out.println("Transfer Successful!");
            System.out.println("Transaction ID: " + txnId);
            System.out.println("From Account: " + srcAccNo);
            System.out.println("To Account: " + destAccNo);
            System.out.printf("Amount: INR %.2f%n", amount);
            System.out.println("Status: SUCCESS");
        } else {
            transactions.add(new Transaction(txnId, "TRANSFER", srcAccNo, destAccNo, amount, "FAILED"));
            System.out.println("Transfer Failed!");
            System.out.println("Transaction ID: " + txnId);
            System.out.println("Status: FAILED (Insufficient Balance or Invalid Amount)");
        }
    }

    private static void checkBalance() {
        System.out.print("Enter Account Number: ");
        int accNo = Integer.parseInt(scanner.nextLine());
        Account acc = accounts.get(accNo);

        if (acc == null) {
            System.out.println("Error: Account not found!");
        } else {
            Customer cust = customers.get(acc.getCustomerId());
            System.out.printf("Account Holder: %s | Account No: %d | Balance: INR %.2f%n",
                    cust.getName(), accNo, acc.getBalance());
        }
    }

    private static void viewTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}
