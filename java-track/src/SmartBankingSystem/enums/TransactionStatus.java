package SmartBankingSystem.enums;

/**
 * Outcome of a transaction attempt.
 */
public enum TransactionStatus {
    /** The money movement was applied. */
    SUCCESS,
    /** The attempt was rejected by a business rule; no balance changed. */
    FAILED
}
