package org.example;


import java.io.*;
import java.util.ArrayList;

public class BlockBufferedFile {
    private final int pageSize = 256;
    private String fileName;
    private int pageCounter;
    private ArrayList<Record> writeBuffer;

    private int writeBufferIndex;
    private ArrayList<Record> readBuffer;
    private int readBufferIndex;

    private int discOperation;

    public BlockBufferedFile(String fileName) {
        this.fileName = fileName;
        this.writeBuffer = new ArrayList<>();
        this.writeBufferIndex = 0;
        this.readBuffer = new ArrayList<>();
        this.readBufferIndex = pageSize;
        this.discOperation = 0;
        this.pageCounter = 0;
    }

    public String getFileName() {
        return fileName;
    }

    public Record getNextRecord() {
        if (readBufferIndex >= pageSize) {
            loadPage();
            discOperation++;
            readBufferIndex = 0;
            pageCounter++;
        }
        if (readBuffer.size() <= readBufferIndex || readBuffer.isEmpty()) {
            return null; // koniec pliku
        }
        Record newRecord = readBuffer.get(readBufferIndex);
        readBufferIndex++;
        return newRecord;
    }

    public void setNextRecord(Record record) {


        writeBuffer.add(record);
        writeBufferIndex++;
        if (writeBufferIndex >= pageSize) {
            writePage();
            discOperation++;
            writeBufferIndex = 0;
        }
    }


    private void loadPage() {
        readBuffer.clear();
        int count = 0;

        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            int lineNumber = pageSize * pageCounter;
            long offset = (long) lineNumber * 16;
            raf.seek(offset);
            while (count < pageSize) {
                try {
                    double valueX = raf.readDouble();
                    double valueY = raf.readDouble();
                    Record record = new Record(valueX, valueY);
                    readBuffer.add(record);
                    count++;
                } catch (EOFException e) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void writePage() {
        int count = 0;
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName, true))) {
            while (count < writeBufferIndex) {
                Record record = writeBuffer.get(count);
                dos.writeDouble(record.getX());
                dos.writeDouble(record.getY());
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        writeBuffer.clear();
    }


    public int getDiscOperation() {
        return discOperation;
    }

    //zapis niepełniej strony
    public void writeBufferPage() {
        if (!writeBuffer.isEmpty()) {
            writePage();
            discOperation++;
            writeBufferIndex = 0;
        }
    }

    public int nextIndexToReadInFile() {
        if (pageCounter == 0) {
            return 0;
        } else {
            int page = pageCounter - 1;
            return pageSize * page + readBufferIndex - 1;//- 1 bo element juz odczytany był aby sprawdzić czy koniec serii
        }
    }

    public void clearFile() {
        new ClearFile(fileName);
        this.writeBuffer.clear();
        this.writeBufferIndex = 0;
        this.readBuffer.clear();
        this.readBufferIndex = pageSize;
        this.pageCounter = 0;

    }

    private void loadPagecsv() {
        readBuffer.clear();
        String line;
        int count = 0;
        int currentLine = 0;
        try {
            CSVReader reader = new CSVReader(fileName);
            while (currentLine < pageSize * pageCounter && (line = reader.read()) != null) {
                currentLine++;
            }
            while ((line = reader.read()) != null && count < pageSize) {
                String[] values = line.split(","); // Podział linii na wartości
                Record record = new Record(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
                readBuffer.add(record);
                count++;
            }
            reader.closeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writePagecsv() {
        int count = 0;
        try {
            CSVWriter writer = new CSVWriter(fileName, true);
            while (count < writeBufferIndex) {
                Record record = writeBuffer.get(count);
                writer.write(record.recordToString());
                count++;
            }
            writer.closeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeBuffer.clear();
    }

}
