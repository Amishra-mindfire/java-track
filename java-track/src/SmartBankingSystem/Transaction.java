package SmartBankingSystem;

public class Transaction {
    private String transactionId;
    private String type;
    private int fromAccount;
    private int toAccount;
    private double amount;
    private String status;

    public Transaction(String transactionId, String type, int fromAccount, int toAccount, double amount, String status) {
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
}
