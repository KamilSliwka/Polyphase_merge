package org.example;

public class App 
{
    public static void main( String[] args )
    {
        RandomGenerator randomGenerator = new RandomGenerator(10,"test.csv");
        randomGenerator.generateFileOfRecords();
        InputRecord input = new InputRecord("test.csv");
        input.add();
    }
}
