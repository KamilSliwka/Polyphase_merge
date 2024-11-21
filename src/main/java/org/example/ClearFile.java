package org.example;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ClearFile {

//    public ClearFile(String fileName) {
//        //this.fileName = fileName;
//        try {
//            new FileWriter(this.fileName, false).close(); // Otwórz i zamknij plik, aby go wyczyścić
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//    }

    public ClearFile(String fileName) {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            raf.setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
