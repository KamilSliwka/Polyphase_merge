package org.example;

public class SecondBiggerTapeStrategy implements SwitchTapeStrategy{
    @Override
    public int tapeSwitching(int currentTapeIndex,int numberOfTapes) {
        return (currentTapeIndex + 2) % numberOfTapes;
    }
}
