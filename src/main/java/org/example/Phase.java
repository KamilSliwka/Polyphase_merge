package org.example;

import java.util.ArrayList;

public class Phase {
    private static final int NUMBER_OF_TAPES = 3;

    private  TapesList tapes;

    private int phaseNumber;

    private Record firstPotentialRecordForNextPhase;
    private Record secondPotentialRecordForNextPhase;

    public Phase(ArrayList<String> tapesName,int currentTapeIndex,SwitchTapeStrategy strategy) {
        this.tapes = new TapesList(tapesName,NUMBER_OF_TAPES,currentTapeIndex,strategy);
        this.phaseNumber = 0;
        this.firstPotentialRecordForNextPhase = null;
        this.secondPotentialRecordForNextPhase = null;
    }

    public int getIndexOfCurrentTape(){
        return tapes.getCurrentTapeIndex();
    }

    private Record getNextRecord(int tapeOffset){
        firstPotentialRecordForNextPhase = tapes.getTapeAtOffset(tapeOffset).getBlockBufferedFile().getNextRecord();
        return firstPotentialRecordForNextPhase;
    }

    private Record takeNotNull(){
        if(firstPotentialRecordForNextPhase!=null){
            return firstPotentialRecordForNextPhase;
        }
        else {
            return secondPotentialRecordForNextPhase;
        }
    }
    public boolean phase(int emptySeries){
        tapes.switchToNextTape();
        Record lastRecord = takeNotNull();
        if(phaseNumber == 0) {
            tapes.switchToNextTape();
            lastRecord = getNextRecord(0);
        }
        phaseNumber++;
        int counter =0;

        while(counter<emptySeries){
            tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(lastRecord);
            Record currentRecord = getNextRecord(0);
            if(currentRecord.compareTo(lastRecord)<0){
                counter++;
            }
            lastRecord =currentRecord;
        }
        Record firstRecord = lastRecord;
        Record secondRecord = getNextRecord(1);
        boolean finish = false;
        while(!finish){
            if(secondRecord.compareTo(firstRecord)<0){
                tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(secondRecord);
                Record record =getNextRecord(1);
                if(record == null || record.compareTo(secondRecord)<0){
                    secondPotentialRecordForNextPhase = record;
                    if(record == null){
                        finish =true;
                    }
                   // tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(firstRecord);
                    while(true) {
                        Record newRecord = getNextRecord(0);
                        if(newRecord == null || newRecord.compareTo(firstRecord)<0){
                            if(newRecord == null){
                                finish =true;
                            }
                            tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(firstRecord);
                            firstRecord =newRecord;
                            break;
                        }
                        tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(firstRecord);
                        firstRecord =newRecord;
                    }
                }
                secondRecord =record;
            }
            else{
                tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(firstRecord);
                Record record = getNextRecord(0);
                if(record == null ||record.compareTo(firstRecord)<0){
                    secondPotentialRecordForNextPhase = record;
                    if(record == null){
                        finish =true;
                    }
                    //tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(secondRecord);
                    while(true) {
                        Record newRecord = getNextRecord(1);
                        if(newRecord == null || newRecord.compareTo(secondRecord)<0){
                            if(newRecord == null){
                                finish =true;
                            }
                            tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(secondRecord);
                            secondRecord =newRecord;
                            break;
                        }
                        tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(secondRecord);
                        secondRecord =newRecord;
                    }
                }
                firstRecord =record;
            }
        }
        tapes.writeTape(2);
        tapes.getTapeAtOffset(0).getBlockBufferedFile().clearFile();
        if(takeNotNull() == null){
            tapes.switchToNextTape();
            tapes.switchToNextTape();
            return true;
        }
        return false;
    }

    public TapesList getTapes() {
        return tapes;
    }
}
