package SmartBankingSystem.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import SmartBankingSystem.enums.TransactionStatus;
import SmartBankingSystem.enums.TransactionType;
import SmartBankingSystem.util.CurrencyFormatter;

/**
 * An immutable entry in the transaction log, recorded for both successful and
 * failed attempts.
 */
public class Transaction {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String transactionId;
    private final TransactionType type;
    private final String fromAccount;
    private final String toAccount;
    private final BigDecimal amount;
    private final TransactionStatus status;
    private final String remarks;
    private final LocalDateTime timestamp;

    /**
     * @param transactionId system-generated ID, e.g. {@code TXN000001}
     * @param type          kind of transaction
     * @param fromAccount   account money left, or {@code null} for deposits
     * @param toAccount     account money went to, or {@code null} for withdrawals
     * @param amount        amount involved
     * @param status        whether the transaction was applied
     * @param remarks       reason for a failure, or {@code null} on success
     */
    public Transaction(String transactionId, TransactionType type, String fromAccount, String toAccount,
                       BigDecimal amount, TransactionStatus status, String remarks) {
        this.transactionId = transactionId;
        this.type = type;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.status = status;
        this.remarks = remarks;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() { return transactionId; }
    public TransactionType getType() { return type; }
    public String getFromAccount() { return fromAccount; }
    public String getToAccount() { return toAccount; }
    public BigDecimal getAmount() { return amount; }
    public TransactionStatus getStatus() { return status; }
    public String getRemarks() { return remarks; }
    public LocalDateTime getTimestamp() { return timestamp; }

    /**
     * @param accountNumber account to check
     * @return true if the account is the sender or the receiver of this transaction
     */
    public boolean involves(String accountNumber) {
        return accountNumber.equals(fromAccount) || accountNumber.equals(toAccount);
    }

    @Override
    public String toString() {
        String text = String.format("%s | %s | %-15s | From: %-10s | To: %-10s | %s | %s",
                transactionId,
                timestamp.format(TIME_FORMAT),
                type,
                fromAccount == null ? "N/A" : fromAccount,
                toAccount == null ? "N/A" : toAccount,
                CurrencyFormatter.format(amount),
                status);
        return remarks == null ? text : text + " (" + remarks + ")";
    }
}
