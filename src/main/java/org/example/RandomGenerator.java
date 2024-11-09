package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomGenerator {
    private int numberOfRecords;
    private String nameFile;
    private double min = 0.0;
    private double max = 10.0;
    
    public RandomGenerator(int numberOfRecords, String nameFile) {
        this.numberOfRecords = numberOfRecords;
        this.nameFile = nameFile;
    }

    private Record generateRecord(){
        Random random = new Random();
        double randomRecordX = min + (max - min) * random.nextDouble();
        double randomRecordY = min + (max - min) * random.nextDouble();
        return new Record(randomRecordX,randomRecordY);
    }

    public void generateFileOfRecords(){
        try {
//            CSVWriter writer = new CSVWriter(nameFile ,false);
//            writer.write("X,Y");
//            writer.closeFile();
            new FileWriter(nameFile , false).close(); // Otwórz i zamknij plik, aby go wyczyścić
            CSVWriter writer = new CSVWriter(nameFile ,true);
            for (int i = 0; i < numberOfRecords; i++) {
                Record newRecord = generateRecord();
                writer.write(newRecord.recordToString());
            }
            writer.closeFile();
        }
        catch (IOException e){
                e.printStackTrace();
        }
    }
}
