package SmartBankingSystem.exception;

/**
 * Thrown when a customer is registered with an email that already exists.
 */
public class DuplicateCustomerException extends BankingException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message human-readable reason shown to the user
     */
    public DuplicateCustomerException(String message) {
        super(message);
    }
}
