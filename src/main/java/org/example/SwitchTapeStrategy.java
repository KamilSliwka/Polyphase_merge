package org.example;

public interface SwitchTapeStrategy  {
    public int tapeSwitching(int currentTapeIndex,int numberOfTapes);
    public int calculateOffset(int currentTapeIndex,int numberOfTapes,int offset);
}

