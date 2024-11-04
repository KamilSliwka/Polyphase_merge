package org.example;


import java.io.IOException;
import java.util.ArrayList;

public class BlockBufferedFile {

    private final int  pageSize = 256;
    private  ArrayList<Record> writeBuffer;

    private int writeBufferIndex;
    private  ArrayList<Record> readBuffer;
    private int readBufferIndex;

    private int discOperation;
    public BlockBufferedFile() {
        this.writeBuffer = new ArrayList<>();
        this.writeBufferIndex = 0;
        this.readBuffer = new ArrayList<>();
        this.readBufferIndex = pageSize;
        this.discOperation = 0;
    }

    public Record getNextRecord(String fileName){
        if(readBufferIndex >= pageSize){
            loadPage(fileName);
            discOperation++;
            readBufferIndex = 0;
        }
        if (readBuffer.isEmpty()) {
            return null; // koniec pliku
        }
        Record newRecord =  readBuffer.get(readBufferIndex);
        readBufferIndex++;
        return newRecord;
    }

    public void setNextRecord(Record record , String fileName){
        //dodac zapis niepełniej strony

        writeBuffer.add(record);
        writeBufferIndex++;
        if(writeBufferIndex >= pageSize){
            writePage(fileName);
            discOperation++;
            writeBufferIndex = 0;
        }
    }

    private void loadPage(String fileName)  {
        readBuffer.clear();
        String line;
        int count = 0;
        try {
            CSVReader reader = new CSVReader(fileName);
            while ((line = reader.read()) != null && count < pageSize) {
                String[] values = line.split(","); // Podział linii na wartości
                Record record = new Record(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
                readBuffer.add(record);
                count++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    private void writePage(String fileName) {
        int count = 0;
        try {
            CSVWriter writer = new CSVWriter(fileName,true);
            while (count < writeBufferIndex) {
                Record record = writeBuffer.get(count);
                writer.write(record.recordToString());
                count++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        writeBuffer.clear();
    }
}
