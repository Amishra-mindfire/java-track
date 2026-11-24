package SmartBankingSystem.entity;

public class Account {
    private int accountNumber;
    private int customerId;
    private double balance;

    public Account(int accountNumber, int customerId, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = initialDeposit;
    }

    public int getAccountNumber() { return accountNumber; }
    public int getCustomerId() { return customerId; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}
