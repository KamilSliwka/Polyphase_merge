package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.FileCopy.binToCsv;
import static org.example.FileCopy.copyCsv;
import static org.example.PrintFile.printFile;

public class Sort {
    private Distribution distribution;
    private int discOperation;
    private Phase phase;

    public Sort(String inputFile, boolean ascending) {
        ArrayList<String> tapes = new ArrayList<String>();
        tapes.add("tape1.bin");
        tapes.add("tape2.bin");
        tapes.add("tape3.bin");
        for (String tape : tapes) {
            ClearFile clearFile = new ClearFile(tape);
        }
        this.discOperation = 0;
        this.distribution = new Distribution(inputFile, tapes, ascending);
        this.phase = new Phase(tapes, 0, new FirstBiggerTapeStrategy(), ascending);
    }

    private void printAllTapesExceptCurrent() {
        System.out.println();
        System.out.println("Faza: " + phase.getPhaseNumber());
        printFile(phase.getFileNameAtOffset(1), phase.getOffsetInFile(1));
        printFile(phase.getFileNameAtOffset(2), phase.getOffsetInFile(2));

    }

    private void printCurrentTape() {
        System.out.println();
        System.out.println("Faza: " + phase.getPhaseNumber());
        printFile(phase.getFileNameAtOffset(0), phase.getOffsetInFile(0));
    }

    private void printTapesAfterDistribution(){
        System.out.println();
        System.out.println("Po Dystrybucji: ");
        printFile("tape1.bin", 0);
        printFile("tape2.bin", 0);
    }
    public List<Integer> sorting(boolean print) {
        System.out.println("Plik początkowy: ");
        printFile("test.bin", 0);

        List<Integer> results = distribution.distribute();

        printTapesAfterDistribution();

        int dummySeries = results.get(0);
        discOperation += results.get(1);
        int currentTapeIndex = results.get(2);
        if (currentTapeIndex == 1) {
            phase.getTapes().setCurrentTapeIndex(1);
            phase.getTapes().setStrategy(new SecondBiggerTapeStrategy());
        }

        boolean isSorted = phase.phase(dummySeries);
        int index = 0;
        while (!isSorted) {
            if (print) {
                printAllTapesExceptCurrent();
            }
            isSorted = phase.phase(0);
        }

        discOperation += phase.getTotalNumberOfOperations();
        System.out.println();
        printCurrentTape();
        System.out.println();
        System.out.println("Liczba operacji dyskowych: " + discOperation);
        System.out.println("Liczba faz sortowania: " + phase.getPhaseNumber());


        index = phase.getIndexOfCurrentTape();
        String sourceFilePath = "tape" + ++index + ".bin";
        String destinationFilePath = "result.csv";
        //copyCsv(sourceFilePath, destinationFilePath);
        binToCsv(sourceFilePath, destinationFilePath);

        return Arrays.asList(discOperation, phase.getPhaseNumber());
    }
}
