package org.example;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputRecord {
    private String nameFile;

    private Scanner scanner;
    public InputRecord(String nameFile) {
        this.nameFile = nameFile;
        this.scanner = new Scanner(System.in);
    }

    public void addx(){

        double x = input("x");
        double y = input("y");
        scanner.close();
        Record record = new Record(x,y);
        try {
            CSVWriter writer = new CSVWriter(nameFile ,true);
            writer.write(record.recordToString());
            writer.closeFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public void add() {
        try  { // Try-with-resources dla zapisu
            boolean continueAdding = true;
            CSVWriter writer = new CSVWriter(nameFile ,false);
            while (continueAdding) {
                double x = input("x");
                double y = input("y");
                Record record = new Record(x, y);

                writer.write(record.recordToString()); // Zapis rekordu do pliku

                continueAdding = askToContinue();
            }
            writer.closeFile();
        } catch (IOException e) {
            System.err.println("Błąd: nie udało się zapisać danych do pliku.");
            e.printStackTrace();
        }
    }

    private double input(String s) {

        double value =0.0;
        boolean isValid = false;
        while (!isValid) {
            System.out.print("Podaj wartość "+s+": ");
            try {
                value = scanner.nextDouble();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Błąd: podano nieprawidłowy format liczby. Spróbuj ponownie.");
                //scanner.next();
            }finally {
                scanner.nextLine(); // czyszczenie bufora wejściowego
            }
        }

        return value;
    }

    private boolean askToContinue() {
        while (true) {
            System.out.print("Czy chcesz dodać kolejny rekord? (T/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("T")) {
                return true; // Kontynuuj dodawanie rekordów
            } else if (input.equals("N")) {
                return false; // Zakończ pętlę
            } else {
                System.out.println("Błąd: podaj 'T' dla tak lub 'N' dla nie.");
            }
        }
    }
}
