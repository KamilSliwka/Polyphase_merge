package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.example.CsvFileCopy.copyCsv;

public class Sort {
    private Distribution distribution;
    private int discOperation;
    private Phase phase;

    public Sort( String inputFile,boolean ascending) {
        ArrayList<String> tapes = new ArrayList<String>();
        tapes.add("tape1.csv");
        tapes.add("tape2.csv");
        tapes.add("tape3.csv");
        for (String tape : tapes) {
            ClearFile clearFile = new ClearFile(tape);
        }
        this.discOperation = 0;
        this.distribution = new Distribution(inputFile,tapes,ascending);
        this.phase = new Phase(tapes,0,new FirstBiggerTapeStrategy(),ascending);
    }
    public void sorting(){
        List<Integer> results = distribution.distribute();
        int dummySeries = results.get(0);
        discOperation += results.get(1);
        int currentTapeIndex = results.get(2);
        if(currentTapeIndex==1){
            phase.getTapes().setCurrentTapeIndex(1);
            phase.getTapes().setStrategy(new SecondBiggerTapeStrategy());
        }

        boolean isSorted = phase.phase(dummySeries);
        int index = 0;
        int phaseNumber = 1;
        while(!isSorted){
            index = phase.getIndexOfCurrentTape();
            //draw 2 tape
            isSorted =phase.phase(0);
            phaseNumber++;
        }
        discOperation += phase.getTotalNumberOfOperations();
        System.out.println("Liczba operacji dyskowych: "+ discOperation);
        System.out.println("Liczba faz sortowania: "+ phaseNumber);

        index = phase.getIndexOfCurrentTape();
        //draw 1 tape

        String sourceFilePath = "tape"+ ++index +".csv";
        String destinationFilePath = "result.csv";
        copyCsv(sourceFilePath, destinationFilePath);
    }
}
