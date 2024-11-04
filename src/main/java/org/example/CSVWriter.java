package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private BufferedWriter writer;
    public CSVWriter(String filePath,boolean flag) throws IOException {
        writer = new BufferedWriter(new FileWriter(filePath,flag));
    }

    public void write(String record) throws IOException{
        writer.write(record);
        writer.newLine();
    }
    public void closeFile() throws IOException {
        writer.close();
    }
}
