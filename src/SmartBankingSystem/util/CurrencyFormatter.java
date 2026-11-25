package SmartBankingSystem.util;

import java.math.BigDecimal;

/**
 * Formats money consistently across messages, receipts and history.
 */
public final class CurrencyFormatter {

    private CurrencyFormatter() {
    }

    /**
     * @param amount the amount to format
     * @return the amount as text, e.g. {@code INR 2,000.00}
     */
    public static String format(BigDecimal amount) {
        return String.format("INR %,.2f", amount);
    }
}
