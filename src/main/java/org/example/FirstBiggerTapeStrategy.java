package org.example;

public class FirstBiggerTapeStrategy implements SwitchTapeStrategy{
    @Override
    public int tapeSwitching(int currentTapeIndex,int numberOfTapes) {
        return (currentTapeIndex + 2) % numberOfTapes;
    }
    @Override
    public int calculateOffset(int currentTapeIndex,int numberOfTapes,int offset) {
        return (currentTapeIndex + offset * 2) % numberOfTapes;
    }
}
