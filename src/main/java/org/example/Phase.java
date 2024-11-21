package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.SortingOrder.compare;

public class Phase {
    private static final int NUMBER_OF_TAPES = 3;
    private TapesList tapes;
    private int phaseNumber;
    private Record firstPotentialRecordForNextPhase;
    private Record secondPotentialRecordForNextPhase;
    private boolean endPhase;
    private boolean ascendingOrder;

    public Phase(ArrayList<String> tapesName, int currentTapeIndex, SwitchTapeStrategy strategy, boolean ascending) {
        this.tapes = new TapesList(tapesName, NUMBER_OF_TAPES, currentTapeIndex, strategy);
        this.phaseNumber = 0;
        this.firstPotentialRecordForNextPhase = null;
        this.secondPotentialRecordForNextPhase = null;
        this.endPhase = false;
        this.ascendingOrder = ascending;
    }

    public int getTotalNumberOfOperations() {
        return tapes.getTotalNumberOfOperations();
    }

    public int getIndexOfCurrentTape() {
        return tapes.getCurrentTapeIndex();
    }

    private Record getNextRecord(int tapeOffset) {
        firstPotentialRecordForNextPhase = tapes.getTapeAtOffset(tapeOffset).getBlockBufferedFile().getNextRecord();
        return firstPotentialRecordForNextPhase;
    }

    private Record takeNotNull() {
        if (firstPotentialRecordForNextPhase != null) {
            return firstPotentialRecordForNextPhase;
        } else {
            return secondPotentialRecordForNextPhase;
        }
    }

    public TapesList getTapes() {
        return tapes;
    }

    public int getOffsetInFile(int offset) {
        return getTapes().getTapeAtOffset(offset).getBlockBufferedFile().nextIndexToReadInFile();
    }

    public int getPhaseNumber() {
        return phaseNumber;
    }

    public String getFileNameAtOffset(int offset) {
        return getTapes().getTapeAtOffset(offset).getBlockBufferedFile().getFileName();
    }

    private Record addRestSeriesFromOtherTape(Record firstRecord, int tapeIndex) {
        while (true) {
            Record newRecord = getNextRecord(tapeIndex);
            if (newRecord == null || compare(newRecord, firstRecord, ascendingOrder)) {
                if (newRecord == null) {
                    endPhase = true;
                }
                tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(firstRecord);
                firstRecord = newRecord;
                return firstRecord;
            }
            tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(firstRecord);
            firstRecord = newRecord;

        }
    }

    private Record moveSeries(int numberOfSeries, Record record) {
        int counter = 0;
        while (counter < numberOfSeries) {
            tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(record);
            Record currentRecord = getNextRecord(0);
            if (compare(currentRecord, record, ascendingOrder)) {
                counter++;
            }
            record = currentRecord;
        }
        return record;
    }

    private Record takeFirstRecord() {
        Record record = takeNotNull();
        if (phaseNumber == 0) {
            tapes.switchToNextTape();
            record = getNextRecord(0);
        }
        return record;
    }

    private boolean isSorted() {
        if (takeNotNull() == null) {
            tapes.switchToNextTape();
            tapes.switchToNextTape();
            return true;
        }
        return false;
    }

    private void sortedInput(Record record){
        tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(record);
        record = getNextRecord(1);
        while(record != null) {
            tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(record);
            record = getNextRecord(1);
        }

        tapes.writeTape(2);
        tapes.getTapeAtOffset(0).getBlockBufferedFile().clearFile();
        tapes.switchToNextTape();
        tapes.switchToNextTape();
    }
    private List<Record> setNextRecords(Record firstRecord, Record secondRecord, int tapeIndex) {
        tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(secondRecord);
        Record record = getNextRecord(tapeIndex);
        if (record == null || compare(record, secondRecord, ascendingOrder)) {
            secondPotentialRecordForNextPhase = record;
            if (record == null) {
                endPhase = true;
            }
            firstRecord = addRestSeriesFromOtherTape(firstRecord, ++tapeIndex % 2);
        }
        return Arrays.asList(firstRecord, record);

    }

    public boolean phase(int emptySeries) {
        tapes.switchToNextTape();
        endPhase = false;
        Record lastRecord = takeFirstRecord();
        phaseNumber++;
        Record firstTapeRecord = moveSeries(emptySeries, lastRecord);
        Record secondTapeRecord = getNextRecord(1);

        if(firstTapeRecord ==null){
            sortedInput(secondTapeRecord);
            return true;
        }

        while (!endPhase) {
            if (compare(secondTapeRecord, firstTapeRecord, ascendingOrder)) {
                List<Record> records = setNextRecords(firstTapeRecord, secondTapeRecord, 1);
                firstTapeRecord = records.get(0);
                secondTapeRecord = records.get(1);
            } else {
                List<Record> records = setNextRecords(secondTapeRecord, firstTapeRecord, 0);
                secondTapeRecord = records.get(0);
                firstTapeRecord = records.get(1);
            }
        }
        tapes.writeTape(2);
        tapes.getTapeAtOffset(0).getBlockBufferedFile().clearFile();
        return isSorted();
    }

}
