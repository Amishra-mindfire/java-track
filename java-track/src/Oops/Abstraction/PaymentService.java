package Oops.Abstraction;

public interface PaymentService {
    void processPayment(double amount);
    void cancelPayment(int paymentId);
}
