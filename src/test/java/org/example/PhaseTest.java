package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.CsvComparer.compareCsvFiles;
import static org.example.CsvFileCopy.copyCsv;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhaseTest {
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

    private static void preparingFiles(String s) {
        String sourceFilePath = "InputFilesForPhaseTests/"+s+"/inputTape1.csv";
        String destinationFilePath = "tape1.csv";
        copyCsv(sourceFilePath, destinationFilePath);
        sourceFilePath = "InputFilesForPhaseTests/"+s+"/inputTape2.csv";
        destinationFilePath = "tape2.csv";
        copyCsv(sourceFilePath, destinationFilePath);
        sourceFilePath = "InputFilesForPhaseTests/"+s+"/inputTape3.csv";
        destinationFilePath = "tape3.csv";
        copyCsv(sourceFilePath, destinationFilePath);
    }

    @Test
    void testPhaseOne() {
        preparingFiles("FirstTest");
        Phase phase = new Phase(tapes,0,new FirstBiggerTapeStrategy());
        phase.phase(1);
        String filePath1 = "tape3.csv";
        String filePath2 = "InputFilesForPhaseTests/FirstTest/outputTape3.csv";
        compareCsvFiles(filePath1, filePath2);
        assertTrue(compareCsvFiles(filePath1, filePath2), "Błędne wartości wynikowe");
    }

    @Test
    void testPhaseTwo() {
        preparingFiles("SecondTest");
        Phase phase = new Phase(tapes,1,new SecondBiggerTapeStrategy());
        phase.phase(4);
        String filePath1 = "tape3.csv";
        String filePath2 = "InputFilesForPhaseTests/SecondTest/outputTape3.csv";
        compareCsvFiles(filePath1, filePath2);
        assertTrue(compareCsvFiles(filePath1, filePath2), "Błędne wartości wynikowe");
    }
    @Test
    void testPhaseThree() {
        preparingFiles("ThirdTest");
        Phase phase = new Phase(tapes,0,new FirstBiggerTapeStrategy());
        phase.phase(0);
        String filePath1 = "tape3.csv";
        String filePath2 = "InputFilesForPhaseTests/ThirdTest/outputTape3.csv";
        compareCsvFiles(filePath1, filePath2);
        assertTrue(compareCsvFiles(filePath1, filePath2), "Błędne wartości wynikowe");
    }

    @Test
    void testPhaseFourth() {
        preparingFiles("FourthTest");
        Phase phase = new Phase(tapes,1,new SecondBiggerTapeStrategy());
        phase.phase(1);
        String filePath1 = "tape3.csv";
        String filePath2 = "InputFilesForPhaseTests/FourthTest/outputTape3.csv";
        compareCsvFiles(filePath1, filePath2);
        assertTrue(compareCsvFiles(filePath1, filePath2), "Błędne wartości wynikowe");
    }

}
