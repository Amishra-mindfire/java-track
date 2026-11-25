package SmartBankingSystem.entity;

/**
 * A registered bank customer. Immutable once created.
 * <p>
 * Instances are created only by the service layer, which generates the ID and
 * guarantees the email is unique.
 */
public class Customer {
    private final String customerId;
    private final String name;
    private final String email;

    /**
     * @param customerId system-generated ID, e.g. {@code CUST0001}
     * @param name       validated, trimmed full name
     * @param email      validated, lower-cased email
     */
    public Customer(String customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + " | Name: " + name + " | Email: " + email;
    }
}
