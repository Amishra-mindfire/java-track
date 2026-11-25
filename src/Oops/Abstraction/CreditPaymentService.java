package Oops.Abstraction;

public class CreditPaymentService implements PaymentService {

    @Override
    public void processPayment(double amount) {
        IO.println("Processing Credit payment of amount: " + amount);
    }

    @Override
    public void cancelPayment(int paymentId) {
        IO.println("Cancelling Credit payment with ID: " + paymentId);
    }
    
}
