package org.example;

import java.util.ArrayList;
import java.util.List;

public class Sort {


    private Distribution distribution;
    private int discOperation;
    public Sort( String inputFile) {
        ArrayList tapes = new ArrayList<String>();
        tapes.add("tape1.csv");
        tapes.add("tape2.csv");
        tapes.add("tape3.csv");
        this.discOperation = 0;
        this.distribution = new Distribution(inputFile,tapes);
    }

    public void sorting(){
        List<Integer> results = distribution.distribute();
        int dummySeries = results.get(0);
        int discOperation = results.get(1);
        System.out.println("puste: "+ dummySeries+" operacje: "+discOperation);
    }


}
