package SmartBankingSystem.exception;

/**
 * Thrown when no customer exists for the given customer ID.
 */
public class CustomerNotFoundException extends BankingException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message human-readable reason shown to the user
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
