package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Distribution {
    private static final int NUMBER_OF_TAPES = 2;
    private String inputFileName;

    private ArrayList<String> tapesName;

    private  ArrayList<Tape> tapes;

    private int currentTapeIndex ;
    private int amountOfAddSeries;
    private int counter;
    private int currentFibNumber;
    private int previousFibNumber;

    private void initialize() {
        this.currentTapeIndex  = 0;
        this.amountOfAddSeries = 0;
        this.counter =0;
        this.currentFibNumber = 1;
        this.previousFibNumber = 0;
    }
    public Distribution(String inputFileName, ArrayList<String> tapesName) {
        this.inputFileName = inputFileName;
        this.tapesName = tapesName;
        this.tapes = new ArrayList<>();
        tapes.add(new Tape(new BlockBufferedFile(tapesName.get(0))));
        tapes.add(new Tape(new BlockBufferedFile(tapesName.get(1))));
        initialize();

    }

    private Tape getTapeAtOffset(int offset) {
        return tapes.get((currentTapeIndex + offset) % NUMBER_OF_TAPES);
    }

    private Record distributeFirstRecord(BlockBufferedFile input){
        Record lastRecord = input.getNextRecord();
        getTapeAtOffset(0).getBlockBufferedFile().setNextRecord(lastRecord);
        counter++;
        getTapeAtOffset(0).increaseSeries();
        return lastRecord;
    }
    private void writeAllTapes() {
        tapes.forEach(tape -> tape.getBlockBufferedFile().writeBufferPage());
    }

    private void updateFibonacciSeries(){
        int temp = currentFibNumber;
        currentFibNumber = previousFibNumber+currentFibNumber;
        previousFibNumber=temp;
    }
    private boolean isFibonacciSeriesFull() {
        return getTapeAtOffset(0).getSeries() == currentFibNumber;
    }
    private void switchToNextTape() {
        currentTapeIndex = (currentTapeIndex + 1) % NUMBER_OF_TAPES;
    }
    private boolean switchTapeIfSeriesComplete(Record recentRecordFromOtherTape,Record currentRecord){
        updateFibonacciSeries();
        amountOfAddSeries = counter;
        counter = 0;
        switchToNextTape();
        return recentRecordFromOtherTape == null || currentRecord.compareTo(recentRecordFromOtherTape) < 0;
    }
    private boolean isNewSerie(boolean newSeries){
        if(newSeries) {
            counter++;
            getTapeAtOffset(0).increaseSeries();
        }
        return true;
    }
    private int checkout(int i){
        i = i+1;
        return i%2;
    }
    private int totalDiscOperation(BlockBufferedFile input){
        return input.getDiscOperation()+tapes.get(0).getBlockBufferedFile().getDiscOperation()+tapes.get(1).getBlockBufferedFile().getDiscOperation();
    }
    private int numberOfDummySeries(){
        if(!FibonacciChecker.isFibonacci(getTapeAtOffset(1).getSeries()+ getTapeAtOffset(0).getSeries())){
            int temp = getTapeAtOffset(1).getSeries() + amountOfAddSeries;
            return temp - getTapeAtOffset(0).getSeries();
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
               writeAllTapes();
               break;
            }
            if(currentRecord.compareTo(lastRecord)<0){ //jesli tapes.get(currentTapeIndex ) record miejszy to seria zakończona
                if(isFibonacciSeriesFull()){
                    newSeries = switchTapeIfSeriesComplete(recentRecordFromOtherTape ,currentRecord);
                    recentRecordFromOtherTape = lastRecord;
                }
                newSeries = isNewSerie(newSeries);
            }
            getTapeAtOffset(0).getBlockBufferedFile().setNextRecord(currentRecord);
            lastRecord =currentRecord;
        }
        return Arrays.asList(numberOfDummySeries(),totalDiscOperation(inputFile));
    }

}
