package Oops.Abstraction;

public abstract class BaseSwitch {
    boolean lightStatus = false;
    
    void turnOn() {
        lightStatus = true;
        IO.println("Switch is turned ON" + (lightStatus ? " and light is ON" : " and light is OFF"));
    }

    abstract void turnOff();
}
