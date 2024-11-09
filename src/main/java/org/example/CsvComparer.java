package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvComparer {

    public static List<double[]> readCsv(String filePath) {
        List<double[]> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    try {
                        double x = Double.parseDouble(values[0]);
                        double y = Double.parseDouble(values[1]);
                        records.add(new double[]{x, y});
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

    public static boolean compareCsvFiles(String filePath1, String filePath2) {
        List<double[]> records1 = readCsv(filePath1);
        List<double[]> records2 = readCsv(filePath2);

        if (records1.size() != records2.size()) {
            return false;
        }

        for (int i = 0; i < records1.size(); i++) {
            double[] record1 = records1.get(i);
            double[] record2 = records2.get(i);

            if (record1[0] != record2[0] || record1[1] != record2[1]) {
                return false;
            }
        }

        return true;
    }

}
