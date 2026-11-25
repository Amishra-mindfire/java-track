package SmartBankingSystem.controller;

import java.util.List;

import SmartBankingSystem.entity.Account;
import SmartBankingSystem.entity.Customer;
import SmartBankingSystem.entity.Transaction;
import SmartBankingSystem.exception.InvalidInputException;
import SmartBankingSystem.service.SmartBankingSystem;
import SmartBankingSystem.validation.InputValidator;

/**
 * Entry point between the user interface and the service layer.
 * <p>
 * Every method takes raw text exactly as the user typed it, checks and converts
 * its format with {@link InputValidator}, then hands clean values to the service,
 * which applies the business rules. The UI therefore holds no validation logic.
 * <p>
 * Format problems raise {@link InvalidInputException}; rule violations raise the
 * service's own {@code BankingException} subclasses. Both share the same base type.
 */
public class BankingController {

    private final SmartBankingSystem bankingService;

    /**
     * @param bankingService the service that holds the business logic
     */
    public BankingController(SmartBankingSystem bankingService) {
        this.bankingService = bankingService;
    }

    /** Registers a customer from a raw name and email. */
    public Customer createCustomer(String rawName, String rawEmail) {
        String name = InputValidator.validateName(rawName);
        String email = InputValidator.validateEmail(rawEmail);
        return bankingService.createCustomer(name, email);
    }

    /** Opens a pending account for a raw customer ID. */
    public Account openAccount(String rawCustomerId) {
        return bankingService.openAccount(InputValidator.validateCustomerId(rawCustomerId));
    }

    /** Funds and activates a pending account. */
    public Transaction makeInitialDeposit(String rawAccountNumber, String rawAmount) {
        return bankingService.makeInitialDeposit(
                InputValidator.validateAccountNumber(rawAccountNumber),
                InputValidator.parseAmount(rawAmount));
    }

    /** Deposits into an active account. */
    public Transaction deposit(String rawAccountNumber, String rawAmount) {
        return bankingService.deposit(
                InputValidator.validateAccountNumber(rawAccountNumber),
                InputValidator.parseAmount(rawAmount));
    }

    /** Withdraws from an active account. */
    public Transaction withdraw(String rawAccountNumber, String rawAmount) {
        return bankingService.withdraw(
                InputValidator.validateAccountNumber(rawAccountNumber),
                InputValidator.parseAmount(rawAmount));
    }

    /** Transfers between two active accounts. */
    public Transaction transfer(String rawSourceAccount, String rawDestinationAccount, String rawAmount) {
        return bankingService.transfer(
                InputValidator.validateAccountNumber(rawSourceAccount),
                InputValidator.validateAccountNumber(rawDestinationAccount),
                InputValidator.parseAmount(rawAmount));
    }

    /** Looks up an account, e.g. to show its balance and status. */
    public Account getAccount(String rawAccountNumber) {
        return bankingService.getAccount(InputValidator.validateAccountNumber(rawAccountNumber));
    }

    /** Returns the transaction history of an account. */
    public List<Transaction> getTransactionHistory(String rawAccountNumber) {
        return bankingService.getTransactionHistory(InputValidator.validateAccountNumber(rawAccountNumber));
    }

    /** Returns every registered customer. */
    public List<Customer> getAllCustomers() {
        return bankingService.getAllCustomers();
    }
}
