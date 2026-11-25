package SmartBankingSystem.enums;

/**
 * Kind of money movement recorded in the transaction log.
 */
public enum TransactionType {
    /** First deposit that activates a pending account. */
    INITIAL_DEPOSIT,
    /** Money added to an account. */
    CREDIT,
    /** Money withdrawn from an account. */
    DEBIT,
    /** Money moved from one account to another. */
    TRANSFER
}
