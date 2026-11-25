package Oops.Inheritance;

public class Main {
    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle(4, 1, 4);
        vehicle.move();

        Car car = new Car(4, 1, 5);
        car.move();
        IO.println("Current gear for car -> " + car.currentGear);
        car.increaseGear();
        IO.println("Current gear for car -> " + car.currentGear);

        Bike bike = new Bike(2, 1, 6);
        bike.doWheelie();
        bike.stopWheelie();
    }
}
