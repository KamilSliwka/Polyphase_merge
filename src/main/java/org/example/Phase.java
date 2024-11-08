package org.example;

import java.util.ArrayList;

public class Phase {
    private static final int NUMBER_OF_TAPES = 3;

    private  TapesList tapes;

    public Phase(ArrayList<String> tapesName,int currentTapeIndex,SwitchTapeStrategy strategy) {
        this.tapes = new TapesList(tapesName,NUMBER_OF_TAPES,currentTapeIndex,strategy);
    }

    public void phase(int emptySeries){
        tapes.switchToNextTape();
        tapes.switchToNextTape();
        int counter =0;
        Record lastRecord = tapes.getTapeAtOffset(0).getBlockBufferedFile().getNextRecord();
        while(counter<emptySeries){
            tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(lastRecord);
            Record currentRecord = tapes.getTapeAtOffset(0).getBlockBufferedFile().getNextRecord();
            if(currentRecord.compareTo(lastRecord)<0){
                counter++;
            }
            lastRecord =currentRecord;
        }
        Record firstRecord = lastRecord;
        Record secondRecord = tapes.getTapeAtOffset(1).getBlockBufferedFile().getNextRecord();
        boolean finish = false;
        while(!finish){
            if(secondRecord.compareTo(firstRecord)<0){
                tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(secondRecord);
                Record record = tapes.getTapeAtOffset(1).getBlockBufferedFile().getNextRecord();
                if(record == null || record.compareTo(secondRecord)<0){
                    if(record == null){
                        finish =true;
                    }
                    tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(firstRecord);
                    while(true) {
                        Record newRecord = tapes.getTapeAtOffset(0).getBlockBufferedFile().getNextRecord();
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
                Record record = tapes.getTapeAtOffset(0).getBlockBufferedFile().getNextRecord();
                if(record == null ||record.compareTo(firstRecord)<0){
                    if(record == null){
                        finish =true;
                    }
                    tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(secondRecord);
                    while(true) {
                        Record newRecord = tapes.getTapeAtOffset(1).getBlockBufferedFile().getNextRecord();
                        if(newRecord == null || newRecord.compareTo(secondRecord)<0){
                            if(newRecord == null){
                                finish =true;
                            }
                            secondRecord =newRecord;
                            break;
                        }
                        secondRecord =newRecord;
                        tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(secondRecord);
                    }
                }
                firstRecord =record;
            }
        }
        tapes.writeTape(2);
        tapes.getTapeAtOffset(0).getBlockBufferedFile().clearFile();

    }

    public TapesList getTapes() {
        return tapes;
    }
}
