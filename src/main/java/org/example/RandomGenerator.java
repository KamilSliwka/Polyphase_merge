package org.example;

import java.util.Random;

public class RandomGenerator {
    int numberOfRecords;
    String nameFile;
    double min = 0.0;
    double max = 100.0;
    
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
        for(int i = 0; i < numberOfRecords;i++){
            Record newRecord = generateRecord();
            //zapisz do pliku
        }
    }
}
