package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Distribution {
    private String inputFileName;

    private ArrayList<String> tapesName;


    public Distribution(String inputFileName, ArrayList<String> tapes) {
        this.inputFileName = inputFileName;
        this.tapesName = tapes;
    }


    private int checkout(int i){
        i = i+1;
        return i%2;
    }
    public List<Integer> distribute(){
        try {
            new FileWriter(tapesName.get(0) , false).close(); // Otwórz i zamknij plik, aby go wyczyścić
            new FileWriter(tapesName.get(1), false).close(); // Otwórz i zamknij plik, aby go wyczyścić
        }
        catch (IOException e){
            e.printStackTrace();
        }
        BlockBufferedFile inputFile = new BlockBufferedFile(inputFileName);
        Tape firstTape = new Tape(new BlockBufferedFile(tapesName.get(0)));
        Tape secondTape = new Tape(new BlockBufferedFile(tapesName.get(1)));
        ArrayList<Tape> tapes = new ArrayList<>();
        tapes.add(firstTape);
        tapes.add(secondTape);
        int index = 0;
        int fibNumber = 0;
        int temp =1;
        Tape current = tapes.get(index);
        Record lastRecord = inputFile.getNextRecord();
        current.getBlockBufferedFile().setNextRecord(lastRecord);
        current.increaseSeries();
        while(true){
            Record currentRecord = inputFile.getNextRecord();
            if(currentRecord == null){
                tapes.get(0).getBlockBufferedFile().writeBufferPage();
                tapes.get(1).getBlockBufferedFile().writeBufferPage();
                break;
            }
            if(currentRecord.compareTo(lastRecord)<0){ //jesli current record miejszy to seria zakończona
                if(FibonacciChecker.isFibonacci(current.getSeries())&&FibonacciChecker.isFibonacci(tapes.get(checkout(index)).getSeries() + current.getSeries()) ){
                    fibNumber = temp;
                    temp = 0;
                    index++;
                    index = index % 2;
                    current = tapes.get(index);

                }
                temp++;
                current.increaseSeries();
            }
            current.getBlockBufferedFile().setNextRecord(currentRecord);
            lastRecord =currentRecord;


        }

        int discOperation = inputFile.getDiscOperation()+tapes.get(0).getBlockBufferedFile().getDiscOperation()+tapes.get(1).getBlockBufferedFile().getDiscOperation();
        temp = tapes.get(checkout(index)).getSeries() + fibNumber;
        int numberOfDummySeries = temp - current.getSeries();

        return Arrays.asList(numberOfDummySeries,discOperation);



    }
}
