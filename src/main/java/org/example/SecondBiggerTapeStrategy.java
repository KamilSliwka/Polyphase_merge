package org.example;

public class SecondBiggerTapeStrategy implements SwitchTapeStrategy{

    @Override
    public int tapeSwitching(int currentTapeIndex,int numberOfTapes) {
        return (currentTapeIndex + 1) % numberOfTapes;
    }
    @Override
    public int calculateOffset(int currentTapeIndex,int numberOfTapes,int offset) {
        return (currentTapeIndex + offset ) % numberOfTapes;
    }
}
