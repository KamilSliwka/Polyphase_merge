package org.example;

import java.util.ArrayList;

public class TapesList {
    private ArrayList<Tape> tapes;

    private SwitchTapeStrategy strategy;

    private int numberOfTapes;

    private int currentTapeIndex;


    public TapesList(ArrayList<String> tapesName, int numberOfTapes, int currentTapeIndex, SwitchTapeStrategy strategy) {
        this.numberOfTapes = numberOfTapes;
        this.currentTapeIndex = currentTapeIndex;
        this.strategy = strategy;
        this.tapes = new ArrayList<>();
        for (String s : tapesName) {
            tapes.add(new Tape(new BlockBufferedFile(s)));
        }
    }

    public int getTotalNumberOfOperations() {
        int discOpertions = 0;
        for (int i = 0; i < tapes.size(); i++) {
            discOpertions += tapes.get(i).getBlockBufferedFile().getDiscOperation();
        }
        return discOpertions;
    }


    public void setStrategy(SwitchTapeStrategy strategy) {
        this.strategy = strategy;
    }

    public int getCurrentTapeIndex() {
        return currentTapeIndex;
    }

    public void setCurrentTapeIndex(int currentTapeIndex) {
        this.currentTapeIndex = currentTapeIndex;
    }

    public Tape getTapeAtOffset(int offset) {
        return tapes.get(strategy.calculateOffset(currentTapeIndex, numberOfTapes, offset));
    }

    public void writeAllTapes() {
        tapes.forEach(tape -> tape.getBlockBufferedFile().writeBufferPage());
    }

    public void writeTape(int index) {
        getTapeAtOffset(index).getBlockBufferedFile().writeBufferPage();
    }

    public void switchToNextTape() {
        currentTapeIndex = strategy.tapeSwitching(currentTapeIndex, numberOfTapes);
    }
}
