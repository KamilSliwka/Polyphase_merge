package org.example;

public class App 
{
    public static void main( String[] args )
    {
        RandomGenerator randomGenerator = new RandomGenerator(100,"test.csv");
        randomGenerator.generateFileOfRecords();
        Sort sort = new Sort("test.csv",true);
        sort.sorting(false);
    }
}
