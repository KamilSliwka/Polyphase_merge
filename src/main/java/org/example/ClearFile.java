package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class ClearFile {
    private String fileName;

    public ClearFile(String fileName) {
        this.fileName = fileName;
        try {
            new FileWriter(this.fileName, false).close(); // Otwórz i zamknij plik, aby go wyczyścić
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
