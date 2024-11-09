package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistributionTest {
    private Distribution distributionClass;
    private ArrayList<String> tapes;
    @BeforeEach
    void setUp() throws IOException {
        this.tapes = new ArrayList<String>();
        tapes.add("tape1.csv");
        tapes.add("tape2.csv");
        tapes.add("tape3.csv");
        for (String tape : this.tapes) {
            ClearFile clearFile = new ClearFile(tape);
        }
    }

    @Test
    void testDistributeOne() {
        distributionClass = new Distribution("InputFilesForDistributionTests/inputTest1.csv", tapes);
        List<Integer> results = distributionClass.distribute();
        int expectedNumberOfDummySeries = 2; // Przykładowa oczekiwana liczba
        int expectedDiscOperations = 6; // Przykładowa oczekiwana liczba operacji dyskowych
        assertEquals(expectedNumberOfDummySeries, results.get(0), "Nieprawidłowa liczba serii pustych");
        assertEquals(expectedDiscOperations, results.get(1), "Nieprawidłowa liczba operacji dyskowych");
    }

    @Test
    void testDistributeTwo() {
        distributionClass = new Distribution("InputFilesForDistributionTests/inputTest2.csv", tapes);
        List<Integer> results = distributionClass.distribute();
        int expectedNumberOfDummySeries = 2; // Przykładowa oczekiwana liczba
        int expectedDiscOperations = 6; // Przykładowa oczekiwana liczba operacji dyskowych
        assertEquals(expectedNumberOfDummySeries, results.get(0), "Nieprawidłowa liczba serii pustych");
        assertEquals(expectedDiscOperations, results.get(1), "Nieprawidłowa liczba operacji dyskowych");
    }
    @Test
    void testDistributeThree() {
        distributionClass = new Distribution("InputFilesForDistributionTests/inputTest3.csv", tapes);
        List<Integer> results = distributionClass.distribute();
        int expectedNumberOfDummySeries = 4; // Przykładowa oczekiwana liczba
        //int expectedDiscOperations = 6; // Przykładowa oczekiwana liczba operacji dyskowych
        assertEquals(expectedNumberOfDummySeries, results.get(0), "Nieprawidłowa liczba serii pustych");
        //assertEquals(expectedDiscOperations, results.get(1), "Nieprawidłowa liczba operacji dyskowych");
    }
}
