package SmartBankingSystem.service;

import java.util.List;

import SmartBankingSystem.entity.Account;
import SmartBankingSystem.entity.Customer;
import SmartBankingSystem.entity.Transaction;

public interface SmartBankingSystem {
    Customer createCustomer(String name, String email);
    Account createAccount(int customerId, double initialDeposit);
    Transaction deposit(int accNo, double amount);
    Transaction withdraw(int accNo, double amount);
    Transaction transfer(int srcAccNo, int destAccNo, double amount);
    double checkBalance(int accNo);
    List<Transaction> viewTransactionHistory(int accNo);
    List<Customer> getAllCustomers();
    Account getAccount(int accNo);
}
