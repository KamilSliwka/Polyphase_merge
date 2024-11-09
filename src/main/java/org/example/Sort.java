package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sort {
    private Distribution distribution;
    private int discOperation;
    private Phase phase;
    public Sort( String inputFile) {
        ArrayList<String> tapes = new ArrayList<String>();
        tapes.add("tape1.csv");
        tapes.add("tape2.csv");
        tapes.add("tape3.csv");
        for (String tape : tapes) {
            ClearFile clearFile = new ClearFile(tape);
        }
        this.discOperation = 0;
        this.distribution = new Distribution(inputFile,tapes);
        this.phase = new Phase(tapes,0,new FirstBiggerTapeStrategy());
    }

    public void sorting(){
        List<Integer> results = distribution.distribute();
        int dummySeries = results.get(0);
        discOperation += results.get(1);
        System.out.println("puste: "+ dummySeries+" operacje: "+discOperation);
        int currentTapeIndex = results.get(2);
        if(currentTapeIndex==1){
            phase.getTapes().setCurrentTapeIndex(1);
            phase.getTapes().setStrategy(new SecondBiggerTapeStrategy());
        }
        //phase.phase(dummySeries);


    }


}
