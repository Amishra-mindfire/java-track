package SmartBankingSystem.exception;

/**
 * Thrown when a transaction is not allowed in the current state, e.g. transferring to the same account or making a second initial deposit.
 */
public class InvalidTransactionException extends BankingException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message human-readable reason shown to the user
     */
    public InvalidTransactionException(String message) {
        super(message);
    }
}
