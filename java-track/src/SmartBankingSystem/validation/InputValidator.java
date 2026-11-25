package SmartBankingSystem.validation;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import SmartBankingSystem.exception.InvalidInputException;
import SmartBankingSystem.util.BankingConstants;

/**
 * Format checks for raw user input. Each method trims, normalises and checks one
 * kind of field, and returns the clean value or throws {@link InvalidInputException}.
 * <p>
 * Only the <em>shape</em> of the input is checked here. Whether the value is
 * acceptable for the bank (account exists, enough balance, ...) is decided by the
 * service layer.
 */
public final class InputValidator {

    private InputValidator() {
    }

    /**
     * @param rawName name as typed by the user
     * @return the trimmed name with repeated spaces collapsed
     * @throws InvalidInputException if blank, too short/long, or containing digits or symbols
     */
    public static String validateName(String rawName) {
        String name = requireNotBlank(rawName, "Name").replaceAll(" +", " ");
        if (name.length() < BankingConstants.NAME_MIN_LENGTH || name.length() > BankingConstants.NAME_MAX_LENGTH) {
            throw new InvalidInputException("Name must be between " + BankingConstants.NAME_MIN_LENGTH
                    + " and " + BankingConstants.NAME_MAX_LENGTH + " characters.");
        }
        requireMatch(name, BankingConstants.NAME_PATTERN,
                "Name can contain only letters, spaces, dots, apostrophes and hyphens.");
        return name;
    }

    /**
     * @param rawEmail email as typed by the user
     * @return the trimmed, lower-cased email
     * @throws InvalidInputException if blank, too long, or not a valid email address
     */
    public static String validateEmail(String rawEmail) {
        String email = requireNotBlank(rawEmail, "Email").toLowerCase();
        if (email.length() > BankingConstants.EMAIL_MAX_LENGTH) {
            throw new InvalidInputException("Email cannot be longer than " + BankingConstants.EMAIL_MAX_LENGTH + " characters.");
        }
        if (email.contains("..")) {
            throw new InvalidInputException("Email cannot contain consecutive dots.");
        }
        requireMatch(email, BankingConstants.EMAIL_PATTERN, "Please enter a valid email address, e.g. name@example.com.");
        return email;
    }

    /**
     * @param rawCustomerId customer ID as typed by the user (case-insensitive)
     * @return the upper-cased customer ID
     * @throws InvalidInputException if not in the form {@code CUST0001}
     */
    public static String validateCustomerId(String rawCustomerId) {
        String customerId = requireNotBlank(rawCustomerId, "Customer ID").toUpperCase();
        requireMatch(customerId, BankingConstants.CUSTOMER_ID_PATTERN,
                "Customer ID must look like " + BankingConstants.CUSTOMER_ID_PREFIX + "0001.");
        return customerId;
    }

    /**
     * @param rawAccountNumber account number as typed by the user (case-insensitive)
     * @return the upper-cased account number
     * @throws InvalidInputException if not in the form {@code SB00000001}
     */
    public static String validateAccountNumber(String rawAccountNumber) {
        String accountNumber = requireNotBlank(rawAccountNumber, "Account number").toUpperCase();
        requireMatch(accountNumber, BankingConstants.ACCOUNT_NUMBER_PATTERN,
                "Account number must be " + BankingConstants.ACCOUNT_NUMBER_PREFIX + " followed by "
                        + BankingConstants.ACCOUNT_NUMBER_DIGITS + " digits, e.g. "
                        + BankingConstants.ACCOUNT_NUMBER_PREFIX + "00000001.");
        return accountNumber;
    }

    /**
     * Parses a money amount. Thousands separators ("2,000") are accepted.
     *
     * @param rawAmount amount as typed by the user
     * @return the amount with two decimal places
     * @throws InvalidInputException if blank, negative, non-numeric, or with more than two decimals
     */
    public static BigDecimal parseAmount(String rawAmount) {
        String amount = requireNotBlank(rawAmount, "Amount").replace(",", "");
        if (amount.startsWith("-")) {
            throw new InvalidInputException("Amount cannot be negative.");
        }
        requireMatch(amount, BankingConstants.AMOUNT_PATTERN,
                "Amount must be a number with at most 2 decimal places, e.g. 2500 or 2500.50.");
        return new BigDecimal(amount).setScale(2);
    }

    /** Shared blank check used by every validator above; returns the trimmed value. */
    private static String requireNotBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }

    /** Shared pattern check used by every validator above. */
    private static void requireMatch(String value, Pattern pattern, String errorMessage) {
        if (!pattern.matcher(value).matches()) {
            throw new InvalidInputException(errorMessage);
        }
    }
}
