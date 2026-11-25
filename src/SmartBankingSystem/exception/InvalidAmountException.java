package SmartBankingSystem.exception;

/**
 * Thrown when an amount is zero, negative, or above the per-transaction limit.
 */
public class InvalidAmountException extends BankingException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message human-readable reason shown to the user
     */
    public InvalidAmountException(String message) {
        super(message);
    }
}
