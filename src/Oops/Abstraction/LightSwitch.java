package Oops.Abstraction;

public class LightSwitch extends BaseSwitch {

    @Override
    void turnOff() {
        lightStatus = false;
        IO.println("Switch is turned OFF" + (lightStatus ? " and light is ON" : " and light is OFF"));
    }
    
}
