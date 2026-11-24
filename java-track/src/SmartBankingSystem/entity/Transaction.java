package SmartBankingSystem.entity;

import SmartBankingSystem.enums.TransactionStatus;
import SmartBankingSystem.enums.TransactionType;

public class Transaction {
    private String transactionId;
    private TransactionType type;
    private int fromAccount;
    private int toAccount;
    private double amount;
    private TransactionStatus status;

    public Transaction(String transactionId, TransactionType type, int fromAccount, int toAccount, double amount, TransactionStatus status) {
        this.transactionId = transactionId;
        this.type = type;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Type: %s | From: %s | To: %s | Amount: INR %.2f | Status: %s",
                transactionId,
                type,
                (fromAccount == 0 ? "N/A" : fromAccount),
                (toAccount == 0 ? "N/A" : toAccount),
                amount,
                status);
    }

    public boolean isFromValidAccount(int accNo) {
        return this.fromAccount == accNo || this.toAccount == accNo;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
