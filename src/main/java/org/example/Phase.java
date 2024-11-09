package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Phase {
    private static final int NUMBER_OF_TAPES = 3;
    private TapesList tapes;
    private int phaseNumber;
    private Record firstPotentialRecordForNextPhase;
    private Record secondPotentialRecordForNextPhase;
    private boolean endPhase;

    public Phase(ArrayList<String> tapesName, int currentTapeIndex, SwitchTapeStrategy strategy) {
        this.tapes = new TapesList(tapesName, NUMBER_OF_TAPES, currentTapeIndex, strategy);
        this.phaseNumber = 0;
        this.firstPotentialRecordForNextPhase = null;
        this.secondPotentialRecordForNextPhase = null;
        this.endPhase = false;
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

    private Record addRestSeriesFromOtherTape(Record firstRecord, int tapeIndex) {
        while (true) {
            Record newRecord = getNextRecord(tapeIndex);
            if (newRecord == null || newRecord.compareTo(firstRecord) < 0) {
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
            if (currentRecord.compareTo(record) < 0) {
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

    private List<Record> setNextRecords(Record firstRecord, Record secondRecord, int tapeIndex) {
        tapes.getTapeAtOffset(2).getBlockBufferedFile().setNextRecord(secondRecord);
        Record record = getNextRecord(tapeIndex);
        if (record == null || record.compareTo(secondRecord) < 0) {
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

        while (!endPhase) {
            if (secondTapeRecord.compareTo(firstTapeRecord) < 0) {
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
