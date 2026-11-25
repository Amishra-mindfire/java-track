package SmartBankingSystem.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Central place for the bank's rules and input formats, so a policy change
 * (for example a different minimum balance) is a one-line edit.
 */
public final class BankingConstants {

    private BankingConstants() {
        // constants holder, not meant to be instantiated
    }

    /** Balance every active account must keep; also the minimum initial deposit. */
    public static final BigDecimal MINIMUM_BALANCE = new BigDecimal("2000.00");

    /** Largest amount allowed in a single deposit, withdrawal or transfer. */
    public static final BigDecimal MAX_TRANSACTION_AMOUNT = new BigDecimal("1000000.00");

    /** Prefix and zero-padded width used when generating customer IDs, e.g. CUST0001. */
    public static final String CUSTOMER_ID_PREFIX = "CUST";
    public static final int CUSTOMER_ID_DIGITS = 4;

    /** Prefix and zero-padded width used when generating account numbers, e.g. SB00000001. */
    public static final String ACCOUNT_NUMBER_PREFIX = "SB";
    public static final int ACCOUNT_NUMBER_DIGITS = 8;

    /** Prefix and zero-padded width used when generating transaction IDs, e.g. TXN000001. */
    public static final String TRANSACTION_ID_PREFIX = "TXN";
    public static final int TRANSACTION_ID_DIGITS = 6;

    public static final int NAME_MIN_LENGTH = 2;
    public static final int NAME_MAX_LENGTH = 50;
    public static final int EMAIL_MAX_LENGTH = 254;

    /**
     * Letters separated by a single space, apostrophe, hyphen, or a dot with an optional space
     * (e.g. "Anita D'Souza", "Ravi Kumar-Singh", "A. P. J. Kalam").
     */
    public static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-z]+(?:(?:[.] ?|[ '-])[A-Za-z]+)*[.]?$");

    /** Practical email check: local part, '@', domain with a 2+ letter top-level domain. */
    public static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(?:[.][A-Za-z0-9-]+)*[.][A-Za-z]{2,}$");

    /** e.g. CUST0001 (at least {@value #CUSTOMER_ID_DIGITS} digits). */
    public static final Pattern CUSTOMER_ID_PATTERN =
            Pattern.compile("^" + CUSTOMER_ID_PREFIX + "[0-9]{" + CUSTOMER_ID_DIGITS + ",}$");

    /** e.g. SB00000001 (exactly {@value #ACCOUNT_NUMBER_DIGITS} digits). */
    public static final Pattern ACCOUNT_NUMBER_PATTERN =
            Pattern.compile("^" + ACCOUNT_NUMBER_PREFIX + "[0-9]{" + ACCOUNT_NUMBER_DIGITS + "}$");

    /** Positive number with at most two decimal places (paise), up to 12 whole digits. */
    public static final Pattern AMOUNT_PATTERN = Pattern.compile("^[0-9]{1,12}([.][0-9]{1,2})?$");
}
