package SmartBankingSystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import SmartBankingSystem.entity.Account;
import SmartBankingSystem.entity.Customer;
import SmartBankingSystem.entity.Transaction;
import SmartBankingSystem.enums.TransactionStatus;
import SmartBankingSystem.enums.TransactionType;

public class SmartBankingSystemImpl implements SmartBankingSystem {

    private static Map<Integer, Customer> customers = new LinkedHashMap<>();
    private static Map<Integer, Account> accounts = new HashMap<>();
    private static List<Transaction> transactions = new ArrayList<>();

    private static int customerIdCounter = 1;
    private static int accountNumberCounter = 1;
    private static int txnIdCounter = 1;

    @Override
    public Customer createCustomer(String name, String email) {
        for (Customer customer : customers.values()) {
            if (customer.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Email already exists: " + email);
            }
            if (customer.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Name already exists: " + name);
            }
        }

        Customer newCustomer = new Customer(customerIdCounter++, name, email);
        customers.put(newCustomer.getCustomerId(), newCustomer);
        return newCustomer;
    }

    @Override
    public Account createAccount(int customerId, double initialDeposit) {
        if (!customers.containsKey(customerId)) {
            throw new IllegalArgumentException("Customer ID does not exist: " + customerId);
        }

        Account newAccount = new Account(accountNumberCounter++, customerId, initialDeposit);
        accounts.put(newAccount.getAccountNumber(), newAccount);
        return newAccount;
    }

    @Override
    public Transaction deposit(int accNo, double amount) {
        Account acc = accounts.get(accNo);
        if (acc == null) {
            throw new IllegalArgumentException("Account not found: " + accNo);
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }

        acc.deposit(amount);
        String txnId = "TXN" + txnIdCounter++;
        transactions.add(new Transaction(txnId, TransactionType.CREDIT, 0, accNo, amount, TransactionStatus.SUCCESS));
        return transactions.get(transactions.size() - 1);
    }

    @Override
    public Transaction withdraw(int accNo, double amount) {
        Account acc = accounts.get(accNo);
        if (acc == null) {
            throw new IllegalArgumentException("Account not found: " + accNo);
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if (acc.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        if(acc.withdraw(amount)) {
            String txnId = "TXN" + txnIdCounter++;
            transactions.add(new Transaction(txnId, TransactionType.DEBIT, accNo, 0, amount, TransactionStatus.SUCCESS));
            return transactions.get(transactions.size() - 1);
        } else {
            throw new IllegalArgumentException("Withdrawal failed due to insufficient balance.");
        }
    }

    @Override
    public Transaction transfer(int srcAccNo, int destAccNo, double amount) {
        Account srcAcc = accounts.get(srcAccNo);
        Account destAcc = accounts.get(destAccNo);

        if (srcAcc == null) {
            throw new IllegalArgumentException("Source account not found: " + srcAccNo);
        }
        if (destAcc == null) {
            throw new IllegalArgumentException("Destination account not found: " + destAccNo);
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }
        if (srcAcc.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds in source account.");
        }

        srcAcc.withdraw(amount);
        destAcc.deposit(amount);
        String txnId = "TXN" + txnIdCounter++;
        transactions.add(new Transaction(txnId, TransactionType.TRANSFER, srcAccNo, destAccNo, amount, TransactionStatus.SUCCESS));
        return transactions.get(transactions.size() - 1);
    }

    @Override
    public double checkBalance(int accNo) {
        Account acc = accounts.get(accNo);
        if (acc == null) {
            throw new IllegalArgumentException("Account not found: " + accNo);
        }
        return acc.getBalance();
    }

    @Override
    public List<Transaction> viewTransactionHistory(int accNo) {
        List<Transaction> accountTransactions = new ArrayList<>();
        for (Transaction txn : transactions) {
            if (txn.isFromValidAccount(accNo)) {
                accountTransactions.add(txn);
            }
        }
        return accountTransactions;
    }    

    @Override 
    public Account getAccount(int accNo) {
        var acc = accounts.get(accNo);
        if (acc == null) {
            throw new IllegalArgumentException("Account not found: " + accNo);
        }
        return acc;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

}
