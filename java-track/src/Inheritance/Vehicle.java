package Inheritance;

public class Vehicle {
    int numWheels;
    int currentGear;
    int maxGear;

    public Vehicle(int numWheels, int currentGear, int maxGear) {
        this.numWheels = numWheels;
        this.currentGear = currentGear;
        this.maxGear = maxGear;
    }

    public void move() {
        IO.println("Vehicle is moving");
    }

    public void increaseGear()
    {
        if(currentGear == maxGear) return;
        currentGear++;
    }
}
