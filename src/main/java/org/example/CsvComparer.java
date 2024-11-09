package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvComparer {

    public static List<Record> readCsv(String filePath) {
        List<Record> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    try {
                        double x = Double.parseDouble(values[0]);
                        double y = Double.parseDouble(values[1]);
                        records.add(new Record(x,y));
                    } catch (NumberFormatException e) {
                        System.out.println("Nieprawidłowy format liczby w pliku " + filePath + ": " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Błąd przy odczycie pliku: " + filePath + " - " + e.getMessage());
        }

        return records;
    }

    public static void writeCsv(String filePath, List<Record> records) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Record record : records) {
                String line = record.getX() + "," + record.getY(); // Zakładamy, że Record ma metody getX() i getY()
                writer.write(line);
                writer.newLine(); // Przejście do nowej linii
            }
            System.out.println("Dane zostały zapisane do pliku: " + filePath);
        } catch (IOException e) {
            System.out.println("Błąd przy zapisywaniu do pliku: " + filePath + " - " + e.getMessage());
        }
    }
    public static boolean compareCsvFiles(String filePath1, String filePath2) {
        List<Record> records1 = readCsv(filePath1);
        List<Record> records2 = readCsv(filePath2);

        if (records1.size() != records2.size()) {
            return false;
        }

        for (int i = 0; i < records1.size(); i++) {
            Record record1 = records1.get(i);
            Record record2 = records2.get(i);
            if (record1.compareTo(record2)!=0) {
                return false;
            }
        }
        return true;
    }

}
