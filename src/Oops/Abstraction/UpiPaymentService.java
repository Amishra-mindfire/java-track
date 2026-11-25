package Oops.Abstraction;

public class UpiPaymentService implements PaymentService {
    @Override
    public void processPayment(double amount) {
        IO.println("Processing UPI payment of amount: " + amount);
    }

    @Override
    public void cancelPayment(int paymentId) {
        IO.println("Cancelling UPI payment with ID: " + paymentId);
    }
}
