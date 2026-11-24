package Oops.Inheritance;

public class Bike extends Vehicle {

    public Bike(int numWheels, int currentGear, int maxGear) {
        super(numWheels, currentGear, maxGear);
    }

    public void doWheelie()
    {
        IO.println("Bike doing wheelie");
    }

    public void stopWheelie()
    {
        IO.println("Bike stopped wheelie");
    }
    
}
