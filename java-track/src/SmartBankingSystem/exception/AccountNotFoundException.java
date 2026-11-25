package SmartBankingSystem.exception;

/**
 * Thrown when no account exists for the given account number.
 */
public class AccountNotFoundException extends BankingException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message human-readable reason shown to the user
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}
