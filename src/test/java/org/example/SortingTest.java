package org.example;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.example.CsvComparer.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortingTest {
    @Test
    void testPhaseOne() {
        Sort sort = new Sort("InputFilesForSortTests/FirstTest/input1.csv",true);
        sort.sorting(false);
        String filePath1 = "result.csv";
        String filePath2 = "InputFilesForSortTests/FirstTest/output.csv";
        compareCsvFiles(filePath1, filePath2);
        assertTrue(compareCsvFiles(filePath1, filePath2), "Błędne wartości wynikowe");
    }

    @Test
    void testPhaseTwo() {
        boolean ascending = true;
        RandomGenerator randomGenerator = new RandomGenerator(100,"test.csv");
        randomGenerator.generateFileOfRecords();

        Sort sort = new Sort("test.csv",ascending);
        sort.sorting(false);

        List<Record> records = readCsv("test.csv");
        if(ascending) {
            records.sort(Comparator.comparingDouble(record -> record.getX()));
        }
        else{
            records.sort(Comparator.comparingDouble(Record::getX).reversed());

        }
        writeCsv("InputFilesForSortTests/SecondTest/output.csv",records);

        String filePath1 = "result.csv";
        String filePath2 = "InputFilesForSortTests/SecondTest/output.csv";
        compareCsvFiles(filePath1, filePath2);
        assertTrue(compareCsvFiles(filePath1, filePath2), "Błędne wartości wynikowe");
    }

}
