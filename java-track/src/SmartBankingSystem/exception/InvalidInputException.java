package SmartBankingSystem.exception;

/**
 * Thrown by the controller layer when raw user input is blank or in the wrong format (bad email, malformed account number, non-numeric amount, ...).
 */
public class InvalidInputException extends BankingException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message human-readable reason shown to the user
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
