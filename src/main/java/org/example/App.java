package org.example;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            showMenu();
            int choice = getChoice();

            switch (choice) {
                case 1 -> taskOne();
                case 2 -> taskTwo();
                case 3 -> taskThree();
                case 4 -> taskFour();
                case 5 -> {
                    System.out.println("Wyjście z programu.");
                    exit = true;
                }
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Sortowanie rekordów bez wyświetlania");
        System.out.println("2. Sortowanie rekordów z wyświetlaniem");
        System.out.println("3. Wpisz rekordy z klawiatury");
        System.out.println("4. Generuj Rekordy");
        System.out.println("5. Wyjście");
        System.out.print("Wybierz opcję: ");
    }

    private static int getChoice() {
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Błąd: Wprowadź liczbę całkowitą.");
        } finally {
            scanner.nextLine(); // czyszczenie bufora wejściowego
        }
        return choice;
    }

    private static int getNumberOfRecords(){
        int userInput =0;
        while (true) {
            System.out.print("Podaj liczbę całkowitą większą od zera: ");
            try {
                userInput = scanner.nextInt();

                if (userInput > 0) {
                    System.out.println("Prawidłowa liczba: " + userInput);
                    break; // Wyjście z pętli, gdy liczba jest poprawna
                } else {
                    System.out.println("Błąd: Liczba musi być większa od zera. Spróbuj ponownie.");
                }
            } catch (Exception e) {
                System.out.println("Błąd: Wprowadź poprawną liczbę całkowitą.");
            } finally {
                scanner.nextLine(); // Czyszczenie bufora wejściowego
            }


        }
        return userInput;
    }

    private static boolean sortOrder(){
        String sortOrder = "";

        while (true) {
            System.out.print("Wybierz sposób sortowania: (R) Rosnąco lub (M) Malejąco: ");
            sortOrder = scanner.nextLine().trim().toUpperCase();

            if (sortOrder.equals("R")) {
                System.out.println("Wybrano sortowanie rosnące.");
               return true;
            } else if (sortOrder.equals("M")) {
                System.out.println("Wybrano sortowanie malejące.");
                return false;
            } else {
                System.out.println("Błąd: Wprowadź 'R' dla rosnącego lub 'M' dla malejącego sortowania.");
            }
        }
    }
    private static void taskOne() {
        System.out.println("Wykonywanie zadania 1");
        boolean ascending = sortOrder();
        Sort sort = new Sort("test.csv",ascending);
        sort.sorting(false);
    }

    private static void taskTwo() {
        System.out.println("Wykonywanie zadania 2");
        boolean ascending = sortOrder();
        Sort sort = new Sort("test.csv",ascending);
        sort.sorting(true);
    }

    private static void taskThree() {
        System.out.println("Wykonywanie zadania 3...");
        InputRecord inputRecord = new InputRecord("test.csv");
        inputRecord.add();
    }

    private static void taskFour() {
        System.out.println("Wykonywanie zadania 4...");
        int numberOfRecords = getNumberOfRecords();
        RandomGenerator randomGenerator = new RandomGenerator(numberOfRecords,"test.csv");
        randomGenerator.generateFileOfRecords();
    }
}

