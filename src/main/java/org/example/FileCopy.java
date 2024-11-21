package org.example;

import java.io.*;

public class FileCopy {
    public static void binToCsv(String sourceFilePath, String destinationFilePath) {
        try (RandomAccessFile raf = new RandomAccessFile(sourceFilePath, "r")) {
            new FileWriter(destinationFilePath, false).close(); // Otwórz i zamknij plik, aby go wyczyścić
            CSVWriter writer = new CSVWriter(destinationFilePath, true);
            while (true) {
                try {
                    double valueX = raf.readDouble(); // Odczytaj 8 bajtów dla X
                    double valueY = raf.readDouble(); // Odczytaj 8 bajtów dla Y
                    Record record = new Record(valueX, valueY);
                    writer.write(record.recordToString());

                } catch (EOFException e) {
                    break;
                }
            }
            writer.closeFile();

        } catch(IOException e){
            System.out.println("Wystąpił błąd podczas kopiowania pliku: " + e.getMessage());
        }
    }
    public static void copyCsv(String sourceFilePath, String destinationFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
             FileWriter writer = new FileWriter(destinationFilePath)) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas kopiowania pliku: " + e.getMessage());
        }
    }

}
