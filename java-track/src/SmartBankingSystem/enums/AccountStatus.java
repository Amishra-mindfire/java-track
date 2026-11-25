package SmartBankingSystem.enums;

/**
 * Lifecycle state of a bank account.
 */
public enum AccountStatus {
    /** Account number has been generated but the minimum initial deposit has not been made yet. */
    PENDING_INITIAL_DEPOSIT,
    /** Initial deposit received; the account can transact normally. */
    ACTIVE
}
