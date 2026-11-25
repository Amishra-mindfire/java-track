package SmartBankingSystem.exception;

/**
 * Thrown when an operation needs an ACTIVE account but the account is still pending its initial deposit.
 */
public class AccountNotActiveException extends BankingException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message human-readable reason shown to the user
     */
    public AccountNotActiveException(String message) {
        super(message);
    }
}
