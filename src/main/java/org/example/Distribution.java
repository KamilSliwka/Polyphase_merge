package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Distribution {
    private static final int NUMBER_OF_TAPES = 2;
    private String inputFileName;
    private  TapesList tapes;

    private int amountOfAddSeries;
    private int counter;
    private int currentFibNumber;
    private int previousFibNumber;

    private void initialize() {
        this.amountOfAddSeries = 0;
        this.counter =0;
        this.currentFibNumber = 1;
        this.previousFibNumber = 0;
    }
    public Distribution(String inputFileName, ArrayList<String> tapesName) {
        this.inputFileName = inputFileName;
        this.tapes = new TapesList(tapesName,NUMBER_OF_TAPES,0,new FirstBiggerTapeStrategy());
        initialize();

    }

    private Record distributeFirstRecord(BlockBufferedFile input){
        Record lastRecord = input.getNextRecord();
        tapes.getTapeAtOffset(0).getBlockBufferedFile().setNextRecord(lastRecord);
        counter++;
        tapes.getTapeAtOffset(0).increaseSeries();
        return lastRecord;
    }

    private void updateFibonacciSeries(){
        int temp = currentFibNumber;
        currentFibNumber = previousFibNumber+currentFibNumber;
        previousFibNumber=temp;
    }
    private boolean isFibonacciSeriesFull() {
        return tapes.getTapeAtOffset(0).getSeries() == currentFibNumber;
    }

    private boolean switchTapeIfSeriesComplete(Record recentRecordFromOtherTape,Record currentRecord){
        updateFibonacciSeries();
        amountOfAddSeries = counter;
        counter = 0;
        tapes.switchToNextTape();
        return recentRecordFromOtherTape == null || currentRecord.compareTo(recentRecordFromOtherTape) < 0;
    }
    private boolean isNewSerie(boolean newSeries){
        if(newSeries) {
            counter++;
            tapes.getTapeAtOffset(0).increaseSeries();
        }
        return true;
    }

    private int totalDiscOperation(BlockBufferedFile input){
        return input.getDiscOperation()+tapes.getTapeAtOffset(0).getBlockBufferedFile().getDiscOperation()+tapes.getTapeAtOffset(1).getBlockBufferedFile().getDiscOperation();
    }
    private int numberOfDummySeries(){
        if(!FibonacciChecker.isFibonacci(tapes.getTapeAtOffset(1).getSeries()+ tapes.getTapeAtOffset(0).getSeries())){
            int temp = tapes.getTapeAtOffset(1).getSeries() + amountOfAddSeries;
            return temp - tapes.getTapeAtOffset(0).getSeries();
        }
        return 0;
    }

    public List<Integer> distribute(){
        BlockBufferedFile inputFile = new BlockBufferedFile(inputFileName);
        Record lastRecord = distributeFirstRecord(inputFile);
        Record recentRecordFromOtherTape = null;
        boolean newSeries = true;
        while(true){
            Record currentRecord = inputFile.getNextRecord();
            if(currentRecord == null){
               tapes.writeAllTapes();
               break;
            }
            if(currentRecord.compareTo(lastRecord)<0){ //jesli tapes.get(currentTapeIndex ) record miejszy to seria zakończona
                if(isFibonacciSeriesFull()){
                    newSeries = switchTapeIfSeriesComplete(recentRecordFromOtherTape ,currentRecord);
                    recentRecordFromOtherTape = lastRecord;
                }
                newSeries = isNewSerie(newSeries);
            }
            tapes.getTapeAtOffset(0).getBlockBufferedFile().setNextRecord(currentRecord);
            lastRecord =currentRecord;
        }
        return Arrays.asList(numberOfDummySeries(),totalDiscOperation(inputFile),tapes.getCurrentTapeIndex());
    }

}
