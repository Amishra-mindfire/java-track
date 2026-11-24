package Oops.Abstraction;

public class Main {
    public static void main(String[] args) {
        PaymentService creditPaymentService = new CreditPaymentService();
        creditPaymentService.processPayment(100.0);
        creditPaymentService.cancelPayment(1);

        PaymentService upiPaymentService = new UpiPaymentService();
        upiPaymentService.processPayment(200.0);
        upiPaymentService.cancelPayment(2);


        // Demonstrating abstraction with LightSwitch
        BaseSwitch lightSwitch = new LightSwitch();
        lightSwitch.turnOn();
        lightSwitch.turnOff();
    }
}
