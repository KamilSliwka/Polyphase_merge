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

    public void add(){

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
                scanner.next();
            }
        }

        return value;
    }
}
