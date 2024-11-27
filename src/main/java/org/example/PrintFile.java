package org.example;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import static org.example.CsvComparer.readCsv;

public class PrintFile {
    private static final int SIZE = 20;

    public static void printFile(String fileName, int offset) {
        List<Record> records = readBin(fileName);
        System.out.println();
        System.out.println("Nazwa pliku: " + fileName);
        int x = Math.min(SIZE, records.size() - offset);
        int newLine = 0;
        for (int i = 0; i < x; i++) {
            System.out.print(records.get(offset + i).toString()+"K:"+String.format("%.3f",records.get(offset + i).vectorLength()));
            if (i < x - 1) {
                System.out.print(" , ");
            }
            newLine++;
            if (newLine == 3) {
                newLine = 0;
                System.out.println();
            }

        }
    }

    public static List<Record> readBin(String filePath) {
        List<Record> records = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            int lineNumber = 0;
            long offset = (long) lineNumber * 16;
            raf.seek(offset);
            while (true) {
                try {
                    double valueX = raf.readDouble();
                    double valueY = raf.readDouble();
                    records.add(new Record(valueX, valueY));
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }


}
