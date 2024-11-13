package org.example;

import java.util.List;

import static org.example.CsvComparer.readCsv;

public class PrintFile {
    private static final int SIZE = 20;
    public static void printFile(String fileName,int offset){
        List<Record> records = readCsv(fileName);
        System.out.println();
        System.out.println("Nazwa pliku: "+ fileName);
        int x = Math.min(SIZE, records.size()-offset);
        int newLine = 0;
        for(int i = 0; i < x ; i++) {
            System.out.print(records.get(offset + i).toString());
            if(i<x-1){
                System.out.print(" , ");
            }
            newLine++;
            if(newLine == 5){
                newLine = 0;
                System.out.println();
            }

        }
    }
}
