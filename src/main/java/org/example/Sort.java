package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.CsvFileCopy.copyCsv;

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
        //couting operations add

        boolean isSorted = phase.phase(dummySeries);
        int index = 0;
        while(!isSorted){
            index = phase.getIndexOfCurrentTape();
            //draw 2 tape
            isSorted =phase.phase(0);

        }
        index = phase.getIndexOfCurrentTape();
        //draw 1 tape
        String sourceFilePath = "tape"+ ++index +".csv";
        String destinationFilePath = "result.csv";
        copyCsv(sourceFilePath, destinationFilePath);
    }
}
