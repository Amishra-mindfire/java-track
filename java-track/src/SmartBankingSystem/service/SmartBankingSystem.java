package SmartBankingSystem.service;

import java.math.BigDecimal;
import java.util.List;

import SmartBankingSystem.entity.Account;
import SmartBankingSystem.entity.Customer;
import SmartBankingSystem.entity.Transaction;
import SmartBankingSystem.exception.AccountNotActiveException;
import SmartBankingSystem.exception.AccountNotFoundException;
import SmartBankingSystem.exception.CustomerNotFoundException;
import SmartBankingSystem.exception.DuplicateCustomerException;
import SmartBankingSystem.exception.InvalidAmountException;
import SmartBankingSystem.exception.InvalidTransactionException;
import SmartBankingSystem.exception.MinimumBalanceException;

/**
 * Business operations of the Smart Banking System.
 * <p>
 * Implementations own every business rule (minimum balance, account status,
 * limits, uniqueness). Callers are expected to pass inputs that are already
 * well-formed (see {@code BankingController}), so this layer checks conditions,
 * not formats.
 * <p>
 * Account lifecycle:
 * {@link #openAccount} creates an account in {@code PENDING_INITIAL_DEPOSIT};
 * {@link #makeInitialDeposit} funds it with at least the minimum balance and makes it {@code ACTIVE};
 * only active accounts can deposit, withdraw or transfer.
 */
public interface SmartBankingSystem {

    /**
     * Registers a new customer.
     *
     * @param name  well-formed full name
     * @param email well-formed, lower-cased email
     * @return the created customer with a generated ID
     * @throws DuplicateCustomerException if the email is already registered
     */
    Customer createCustomer(String name, String email);

    /**
     * Opens a new account for an existing customer. The account number is generated
     * immediately, but the account stays pending until {@link #makeInitialDeposit} is called.
     *
     * @param customerId ID of the owning customer
     * @return the new account, with zero balance and status {@code PENDING_INITIAL_DEPOSIT}
     * @throws CustomerNotFoundException if the customer does not exist
     */
    Account openAccount(String customerId);

    /**
     * Makes the first deposit into a pending account and activates it.
     *
     * @param accountNumber account to fund
     * @param amount        must be at least the minimum balance
     * @return the recorded transaction
     * @throws AccountNotFoundException     if the account does not exist
     * @throws InvalidTransactionException  if the account is already active
     * @throws InvalidAmountException       if the amount is not positive or exceeds the transaction limit
     * @throws MinimumBalanceException      if the amount is below the minimum balance
     */
    Transaction makeInitialDeposit(String accountNumber, BigDecimal amount);

    /**
     * Deposits money into an active account.
     *
     * @param accountNumber account to credit
     * @param amount        positive amount within the transaction limit
     * @return the recorded transaction
     * @throws AccountNotFoundException   if the account does not exist
     * @throws AccountNotActiveException  if the account is still pending
     * @throws InvalidAmountException     if the amount is not positive or exceeds the transaction limit
     */
    Transaction deposit(String accountNumber, BigDecimal amount);

    /**
     * Withdraws money from an active account, keeping at least the minimum balance.
     *
     * @param accountNumber account to debit
     * @param amount        positive amount within the transaction limit
     * @return the recorded transaction
     * @throws AccountNotFoundException   if the account does not exist
     * @throws AccountNotActiveException  if the account is still pending
     * @throws InvalidAmountException     if the amount is not positive or exceeds the transaction limit
     * @throws MinimumBalanceException    if the withdrawal would leave less than the minimum balance
     *                                    (a FAILED transaction is recorded)
     */
    Transaction withdraw(String accountNumber, BigDecimal amount);

    /**
     * Moves money between two different active accounts, keeping at least the
     * minimum balance in the source account.
     *
     * @param sourceAccountNumber      account to debit
     * @param destinationAccountNumber account to credit
     * @param amount                   positive amount within the transaction limit
     * @return the recorded transaction
     * @throws InvalidTransactionException if source and destination are the same account
     * @throws AccountNotFoundException    if either account does not exist
     * @throws AccountNotActiveException   if either account is still pending
     * @throws InvalidAmountException      if the amount is not positive or exceeds the transaction limit
     * @throws MinimumBalanceException     if the source would be left below the minimum balance
     *                                     (a FAILED transaction is recorded)
     */
    Transaction transfer(String sourceAccountNumber, String destinationAccountNumber, BigDecimal amount);

    /**
     * @param accountNumber account to look up
     * @return the account
     * @throws AccountNotFoundException if the account does not exist
     */
    Account getAccount(String accountNumber);

    /**
     * Lists every transaction (successful and failed) that involves the account, oldest first.
     *
     * @param accountNumber account to look up
     * @return the account's transactions; empty if none
     * @throws AccountNotFoundException if the account does not exist
     */
    List<Transaction> getTransactionHistory(String accountNumber);

    /**
     * @return all customers in registration order
     */
    List<Customer> getAllCustomers();
}
