package com.astontech.example;

public class CarExampleImpl implements CarExample{

    @Override
    public void pushGasPedal() {
        System.out.println("Wire connecting to the throttle is allowing more gas to flow into the engine and therefore accelerate the vehicle");
    }

    @Override
    public void turnWheel() {
        System.out.println("Interacts with the axel of the vehicle to make the tires pivot a certain direction thereby changing your course");
    }
}
