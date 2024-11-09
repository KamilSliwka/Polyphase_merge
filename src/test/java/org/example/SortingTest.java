package org.example;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.example.CsvComparer.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortingTest {
    @Test
    void testPhaseOne() {
        Sort sort = new Sort("InputFilesForSortTests/FirstTest/input1.csv");
        sort.sorting();
        String filePath1 = "result.csv";
        String filePath2 = "InputFilesForSortTests/FirstTest/output.csv";
        compareCsvFiles(filePath1, filePath2);
        assertTrue(compareCsvFiles(filePath1, filePath2), "Błędne wartości wynikowe");
    }

    @Test
    void testPhaseTwo() {
        RandomGenerator randomGenerator = new RandomGenerator(1000,"test.csv");
        randomGenerator.generateFileOfRecords();

        Sort sort = new Sort("test.csv");
        sort.sorting();

        List<Record> records = readCsv("test.csv");
        records.sort(Comparator.comparingDouble(record -> record.getX()));
        writeCsv("InputFilesForSortTests/SecondTest/output.csv",records);

        String filePath1 = "result.csv";
        String filePath2 = "InputFilesForSortTests/SecondTest/output.csv";
        compareCsvFiles(filePath1, filePath2);
        assertTrue(compareCsvFiles(filePath1, filePath2), "Błędne wartości wynikowe");
    }

}
