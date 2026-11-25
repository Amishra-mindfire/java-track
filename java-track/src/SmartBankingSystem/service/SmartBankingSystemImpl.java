package SmartBankingSystem.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import SmartBankingSystem.entity.Account;
import SmartBankingSystem.entity.Customer;
import SmartBankingSystem.entity.Transaction;
import SmartBankingSystem.enums.TransactionStatus;
import SmartBankingSystem.enums.TransactionType;
import SmartBankingSystem.exception.AccountNotActiveException;
import SmartBankingSystem.exception.AccountNotFoundException;
import SmartBankingSystem.exception.CustomerNotFoundException;
import SmartBankingSystem.exception.DuplicateCustomerException;
import SmartBankingSystem.exception.InvalidAmountException;
import SmartBankingSystem.exception.InvalidTransactionException;
import SmartBankingSystem.exception.MinimumBalanceException;
import SmartBankingSystem.util.BankingConstants;
import SmartBankingSystem.util.CurrencyFormatter;

/**
 * In-memory implementation of {@link SmartBankingSystem}.
 * <p>
 * State is held per instance (not {@code static}), so each instance is an
 * independent bank. That keeps tests isolated from each other.
 */
public class SmartBankingSystemImpl implements SmartBankingSystem {

    /** Customers by ID, kept in registration order. */
    private final Map<String, Customer> customers = new LinkedHashMap<>();
    /** Lower-cased emails already registered, for the uniqueness check. */
    private final Set<String> registeredEmails = new HashSet<>();
    private final Map<String, Account> accounts = new HashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();

    private int customerSequence = 1;
    private int accountSequence = 1;
    private int transactionSequence = 1;

    @Override
    public Customer createCustomer(String name, String email) {
        String emailKey = email.toLowerCase();
        if (registeredEmails.contains(emailKey)) {
            throw new DuplicateCustomerException("A customer with email " + email + " already exists.");
        }

        String customerId = generateId(BankingConstants.CUSTOMER_ID_PREFIX,
                BankingConstants.CUSTOMER_ID_DIGITS, customerSequence++);
        Customer customer = new Customer(customerId, name, emailKey);
        customers.put(customerId, customer);
        registeredEmails.add(emailKey);
        return customer;
    }

    @Override
    public Account openAccount(String customerId) {
        if (!customers.containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer not found: " + customerId);
        }

        String accountNumber = generateId(BankingConstants.ACCOUNT_NUMBER_PREFIX,
                BankingConstants.ACCOUNT_NUMBER_DIGITS, accountSequence++);
        Account account = new Account(accountNumber, customerId);
        accounts.put(accountNumber, account);
        return account;
    }

    @Override
    public Transaction makeInitialDeposit(String accountNumber, BigDecimal amount) {
        Account account = findAccount(accountNumber);
        if (account.isActive()) {
            throw new InvalidTransactionException("Account " + accountNumber
                    + " is already active. Use Deposit to add more money.");
        }
        validateAmount(amount);
        if (amount.compareTo(BankingConstants.MINIMUM_BALANCE) < 0) {
            throw new MinimumBalanceException("The initial deposit must be at least "
                    + CurrencyFormatter.format(BankingConstants.MINIMUM_BALANCE) + ".");
        }

        account.credit(amount);
        account.activate();
        return recordSuccess(TransactionType.INITIAL_DEPOSIT, null, accountNumber, amount);
    }

    @Override
    public Transaction deposit(String accountNumber, BigDecimal amount) {
        Account account = findActiveAccount(accountNumber);
        validateAmount(amount);

        account.credit(amount);
        return recordSuccess(TransactionType.CREDIT, null, accountNumber, amount);
    }

    @Override
    public Transaction withdraw(String accountNumber, BigDecimal amount) {
        Account account = findActiveAccount(accountNumber);
        validateAmount(amount);
        ensureMinimumBalanceAfterDebit(account, amount, TransactionType.DEBIT, accountNumber, null);

        account.debit(amount);
        return recordSuccess(TransactionType.DEBIT, accountNumber, null, amount);
    }

    @Override
    public Transaction transfer(String sourceAccountNumber, String destinationAccountNumber, BigDecimal amount) {
        if (sourceAccountNumber.equals(destinationAccountNumber)) {
            throw new InvalidTransactionException("Source and destination accounts must be different.");
        }
        Account source = findActiveAccount(sourceAccountNumber);
        Account destination = findActiveAccount(destinationAccountNumber);
        validateAmount(amount);
        ensureMinimumBalanceAfterDebit(source, amount, TransactionType.TRANSFER,
                sourceAccountNumber, destinationAccountNumber);

        source.debit(amount);
        destination.credit(amount);
        return recordSuccess(TransactionType.TRANSFER, sourceAccountNumber, destinationAccountNumber, amount);
    }

    @Override
    public Account getAccount(String accountNumber) {
        return findAccount(accountNumber);
    }

    @Override
    public List<Transaction> getTransactionHistory(String accountNumber) {
        findAccount(accountNumber);
        List<Transaction> history = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.involves(accountNumber)) {
                history.add(transaction);
            }
        }
        return history;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    // ---------------------------------------------------------------------
    // Helpers shared by the operations above
    // ---------------------------------------------------------------------

    /**
     * @throws AccountNotFoundException if the account does not exist
     */
    private Account findAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        return account;
    }

    /**
     * Looks up an account and checks that it has completed its initial deposit.
     *
     * @throws AccountNotFoundException  if the account does not exist
     * @throws AccountNotActiveException if the account is still pending
     */
    private Account findActiveAccount(String accountNumber) {
        Account account = findAccount(accountNumber);
        if (!account.isActive()) {
            throw new AccountNotActiveException("Account " + accountNumber
                    + " is pending its initial deposit of at least "
                    + CurrencyFormatter.format(BankingConstants.MINIMUM_BALANCE) + ".");
        }
        return account;
    }

    /**
     * Checks the rules every amount must follow: greater than zero and within the per-transaction limit.
     *
     * @throws InvalidAmountException if either rule is broken
     */
    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero.");
        }
        if (amount.compareTo(BankingConstants.MAX_TRANSACTION_AMOUNT) > 0) {
            throw new InvalidAmountException("Amount cannot exceed "
                    + CurrencyFormatter.format(BankingConstants.MAX_TRANSACTION_AMOUNT) + " per transaction.");
        }
    }

    /**
     * Makes sure a debit leaves at least the minimum balance. If it would not, records a
     * FAILED transaction (so the attempt shows up in history) and throws.
     *
     * @throws MinimumBalanceException if the debit would break the minimum balance
     */
    private void ensureMinimumBalanceAfterDebit(Account account, BigDecimal amount, TransactionType type,
                                                String fromAccount, String toAccount) {
        BigDecimal remaining = account.getBalance().subtract(amount);
        if (remaining.compareTo(BankingConstants.MINIMUM_BALANCE) >= 0) {
            return;
        }

        BigDecimal maxAllowed = account.getBalance().subtract(BankingConstants.MINIMUM_BALANCE).max(BigDecimal.ZERO);
        String reason = "A minimum balance of " + CurrencyFormatter.format(BankingConstants.MINIMUM_BALANCE)
                + " must be kept. You can debit at most " + CurrencyFormatter.format(maxAllowed) + ".";
        record(type, fromAccount, toAccount, amount, TransactionStatus.FAILED, reason);
        throw new MinimumBalanceException(reason);
    }

    private Transaction recordSuccess(TransactionType type, String fromAccount, String toAccount, BigDecimal amount) {
        return record(type, fromAccount, toAccount, amount, TransactionStatus.SUCCESS, null);
    }

    /** Creates a transaction with the next ID, appends it to the log and returns it. */
    private Transaction record(TransactionType type, String fromAccount, String toAccount,
                               BigDecimal amount, TransactionStatus status, String remarks) {
        String transactionId = generateId(BankingConstants.TRANSACTION_ID_PREFIX,
                BankingConstants.TRANSACTION_ID_DIGITS, transactionSequence++);
        Transaction transaction = new Transaction(transactionId, type, fromAccount, toAccount, amount, status, remarks);
        transactions.add(transaction);
        return transaction;
    }

    /** Builds IDs such as {@code SB00000001} from a prefix, a width and a sequence number. */
    private static String generateId(String prefix, int digits, int sequence) {
        return prefix + String.format("%0" + digits + "d", sequence);
    }
}
