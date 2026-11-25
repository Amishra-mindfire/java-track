package SmartBankingSystem.exception;

/**
 * Thrown when an initial deposit is below the minimum balance, or a debit would take the balance below it.
 */
public class MinimumBalanceException extends BankingException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message human-readable reason shown to the user
     */
    public MinimumBalanceException(String message) {
        super(message);
    }
}
