package org.example;

public class SortingOrder {
    public static boolean compare(Record record1 ,Record record2,boolean ascending ) {
        if(ascending){
            return record1.compareTo(record2) < 0 ;
        }
        else {
            return record1.compareTo(record2) > 0 ;
        }
    }
}
