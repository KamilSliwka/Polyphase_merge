package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileCopy {
    public static void copyCsv(String sourceFilePath, String destinationFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
             FileWriter writer = new FileWriter(destinationFilePath)) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + System.lineSeparator());
            }
            //System.out.println("Plik został pomyślnie skopiowany.");

        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas kopiowania pliku: " + e.getMessage());
        }
    }

}
