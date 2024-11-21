package org.example;

import java.io.*;
import java.util.Locale;
import java.util.Random;

public class RandomGenerator {
    private int numberOfRecords;
    private String nameFile;
    private double min = 0.0;
    private double max = 100.0;




    public RandomGenerator(int numberOfRecords, String nameFile) {
        this.numberOfRecords = numberOfRecords;
        this.nameFile = nameFile;
    }

    private Record generateRecord() {
        Random random = new Random();
        double randomRecordX = min + (max - min) * random.nextDouble();
        double randomRecordY = min + (max - min) * random.nextDouble();

//        double randomRecordX = (double) (random.nextInt((int) (max - min + 1)) + (int) min);
//        double randomRecordY = (double) (random.nextInt((int) (max - min + 1)) + (int) min);

        return new Record(randomRecordX, randomRecordY);
    }

    public void generateFileOfRecords() {
        try {
            new FileWriter(nameFile, false).close(); // Otwórz i zamknij plik, aby go wyczyścić
            CSVWriter writer = new CSVWriter(nameFile, true);
            for (int i = 0; i < numberOfRecords; i++) {
                Record newRecord = generateRecord();
                writer.write(newRecord.recordToString());
            }
            writer.closeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void generateBinFileOfRecords() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(nameFile))) {
            for (int i = 0; i < numberOfRecords; i++) {
                Record newRecord = generateRecord();
                dos.writeDouble(newRecord.getX());
                dos.writeDouble(newRecord.getY());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
