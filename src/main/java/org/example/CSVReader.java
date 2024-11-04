package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    private BufferedReader reader;
    public CSVReader(String filePath) throws IOException {
        reader = new BufferedReader(new FileReader(filePath));
    }
    public String read() throws IOException{
        return reader.readLine();
    }

}
