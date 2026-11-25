package SmartBankingSystem.exception;

/**
 * Base class for every error raised by the Smart Banking System.
 * <p>
 * It is unchecked so callers are not forced to declare it, but the UI layer
 * catches this single type to show a friendly message for any banking failure.
 */
public class BankingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message human-readable reason shown to the user
     */
    public BankingException(String message) {
        super(message);
    }
}
