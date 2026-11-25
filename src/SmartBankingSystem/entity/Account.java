package SmartBankingSystem.entity;

import java.math.BigDecimal;

import SmartBankingSystem.enums.AccountStatus;

/**
 * A customer's bank account.
 * <p>
 * A new account starts with a zero balance in {@link AccountStatus#PENDING_INITIAL_DEPOSIT}
 * and becomes {@link AccountStatus#ACTIVE} once the minimum initial deposit is made.
 * <p>
 * {@link #credit}, {@link #debit} and {@link #activate} only change state; they do not
 * enforce banking rules. All rule checks (minimum balance, limits, status) live in the
 * service layer, which is the only code that should call these methods.
 */
public class Account {
    private final String accountNumber;
    private final String customerId;
    private BigDecimal balance;
    private AccountStatus status;

    /**
     * Creates a new, not-yet-funded account.
     *
     * @param accountNumber system-generated account number, e.g. {@code SB00000001}
     * @param customerId    ID of the owning customer
     */
    public Account(String accountNumber, String customerId) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = BigDecimal.ZERO.setScale(2);
        this.status = AccountStatus.PENDING_INITIAL_DEPOSIT;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getBalance() { return balance; }
    public AccountStatus getStatus() { return status; }

    /** @return true if the account can transact normally */
    public boolean isActive() {
        return status == AccountStatus.ACTIVE;
    }

    /**
     * Adds money to the balance. Rule checks are the caller's responsibility.
     *
     * @param amount positive amount to add
     */
    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    /**
     * Removes money from the balance. Rule checks are the caller's responsibility.
     *
     * @param amount positive amount to remove
     */
    public void debit(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    /** Marks the account as active after its initial deposit. */
    public void activate() {
        status = AccountStatus.ACTIVE;
    }
}
