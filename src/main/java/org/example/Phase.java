package org.example;

import java.util.ArrayList;

public class Phase {
    private static final int NUMBER_OF_TAPES = 3;

    private  TapesList tapes;

    public Phase(ArrayList<String> tapesName,int currentTapeIndex,SwitchTapeStrategy strategy) {
        this.tapes = new TapesList(tapesName,NUMBER_OF_TAPES,currentTapeIndex,strategy);
    }

    public void phase(int emptySeries){


    }

    public TapesList getTapes() {
        return tapes;
    }
}
