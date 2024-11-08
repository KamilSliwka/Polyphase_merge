package org.example;

public class FirstBiggerTapeStrategy implements SwitchTapeStrategy{
    @Override
    public int tapeSwitching(int currentTapeIndex,int numberOfTapes) {
        return (currentTapeIndex + 1) % numberOfTapes;
    }
}
