package org.example;

import static org.example.SeriesCounter.countAmountOfSeriesInFile;

public class App
{
    public static void main( String[] args )
    {
        RandomGenerator randomGenerator = new RandomGenerator(10,"test.csv");
        randomGenerator.generateFileOfRecords();
        //System.out.println("ilość serii: "+ countAmountOfSeriesInFile("test.csv",true));

        Sort sort = new Sort("test.csv",true);
        sort.sorting(false);
    }
}
