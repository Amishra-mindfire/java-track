package SmartBankingSystem;

import java.util.List;
import java.util.Scanner;

import SmartBankingSystem.entity.Account;
import SmartBankingSystem.entity.Customer;
import SmartBankingSystem.entity.Transaction;
import SmartBankingSystem.service.SmartBankingSystem;
import SmartBankingSystem.service.SmartBankingSystemImpl;

public class Main {
    

    private static Scanner scanner = new Scanner(System.in);
    private static SmartBankingSystem bankingSystem = new SmartBankingSystemImpl();

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
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 9.");
                continue;
            } catch(IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                continue;
            }

           
        }
    }

    private static void createCustomer() {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        var customer = bankingSystem.createCustomer(name, email);
        System.out.println("Customer Created Successfully! Customer ID: " + customer.getCustomerId());
    }

    private static void createAccount() {
        System.out.print("Enter Customer ID: ");
        int customerId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Initial Deposit (INR): ");
        double initialDeposit = Double.parseDouble(scanner.nextLine());
        if (initialDeposit < 0) {
            System.out.println("Error: Initial deposit cannot be negative.");
            return;
        }

        var account = bankingSystem.createAccount(customerId, initialDeposit);
        System.out.println("Account Created Successfully! Account Number: " + account.getAccountNumber());
    }

    private static void depositMoney() {
        System.out.print("Enter Account Number: ");
        int accNo = Integer.parseInt(scanner.nextLine());
        Account acc = bankingSystem.getAccount(accNo);

        System.out.print("Enter Amount to Deposit: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount <= 0) {
            System.out.println("Error: Amount must be greater than zero.");
            return;
        }

        var txn = bankingSystem.deposit(accNo, amount);
        System.out.printf("Deposit Successful! Transaction ID: %s | New Balance: INR %.2f%n", txn.getTransactionId(), acc.getBalance());
    }

    private static void withdrawMoney() {
        System.out.print("Enter Account Number: ");
        int accNo = Integer.parseInt(scanner.nextLine());
        Account acc = bankingSystem.getAccount(accNo);

        System.out.print("Enter Amount to Withdraw: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount <= 0) {
            System.out.println("Error: Amount must be greater than zero.");
            return;
        }

        var txn = bankingSystem.withdraw(accNo, amount);
        if (txn != null) {
            System.out.printf("Withdrawal Successful! Transaction ID: %s | New Balance: INR %.2f%n", txn.getTransactionId(), acc.getBalance());
        } else {
            System.out.println("Withdrawal Failed! Insufficient Balance.");
        }
    }

    private static void transferMoney() {
        System.out.print("Enter Source Account: ");
        int srcAccNo = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Destination Account: ");
        int destAccNo = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        var txn = bankingSystem.transfer(srcAccNo, destAccNo, amount);
        System.out.printf("Transfer Successful! Transaction ID: %s | Amount: INR %.2f%n", txn.getTransactionId(), amount);
    }

    private static void checkBalance() {
        System.out.print("Enter Account Number: ");
        int accNo = Integer.parseInt(scanner.nextLine());
        Account acc = bankingSystem.getAccount(accNo);
        System.out.printf("Current Balance for Account %d: INR %.2f%n", accNo, acc.getBalance());
    }

    private static void viewTransactionHistory() {
        System.out.print("Enter Account Number: ");
        int accNo = Integer.parseInt(scanner.nextLine());

        var transactions = bankingSystem.viewTransactionHistory(accNo);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this account.");
            return;
        }

        System.out.println("\n--- Transaction History ---");
        for (Transaction txn : transactions) {
            System.out.println(txn.toString());
        }
    }

    private static void viewAllCustomers() {
        List<Customer> customers = bankingSystem.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        System.out.println("\n--- Customer List ---");
        for (Customer c : customers) {
            System.out.println(c.toString());
        }
    }
}
