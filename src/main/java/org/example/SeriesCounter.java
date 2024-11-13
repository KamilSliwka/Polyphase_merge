package org.example;

import java.util.List;

import static org.example.CsvComparer.readCsv;
import static org.example.SortingOrder.compare;

public class SeriesCounter {
    public static int countAmountOfSeriesInFile(String inputFile,boolean ascending){
        List<Record> records = readCsv(inputFile);
        int size = records.size() - 1;
        int series = 1;
        for(int i =0;i< size ; i++){
            if(compare(records.get(i+1),records.get(i),ascending)){
                series++;
            }
        }
        return series;


    }
}
