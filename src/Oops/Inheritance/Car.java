package Oops.Inheritance;

public class Car extends Vehicle {

    public Car(int numWheels, int currentGear, int maxGear) {
        super(numWheels, currentGear, maxGear);
    }

    public void holdHandbrake()
    {
        IO.println("Car handbrake pulled.");
    }
    
}
