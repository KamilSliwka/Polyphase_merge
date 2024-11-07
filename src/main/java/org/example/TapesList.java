package org.example;

import java.util.ArrayList;

public class TapesList {
    private ArrayList<Tape> tapes;

    private int numberOfTapes ;

    private int currentTapeIndex ;

    public TapesList( ArrayList<String> tapesName, int numberOfTapes) {
        this.numberOfTapes = numberOfTapes;
        this.currentTapeIndex = 0;
        this.tapes = new ArrayList<>();
        for (String s : tapesName) {
            tapes.add(new Tape(new BlockBufferedFile(s)));
        }
    }


    public Tape getTapeAtOffset(int offset) {
        return tapes.get((currentTapeIndex + offset) % numberOfTapes);
    }
    public void writeAllTapes() {
        tapes.forEach(tape -> tape.getBlockBufferedFile().writeBufferPage());
    }
    public void switchToNextTape() {
        currentTapeIndex = (currentTapeIndex + 1) % numberOfTapes;
    }
}
