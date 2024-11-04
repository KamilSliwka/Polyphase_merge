package org.example;

import java.util.Objects;

public class Record implements Comparable<Record>
{
    private double x;
    private double y;

    public Record(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record)) return false;
        Record record = (Record) o;
        return Double.compare(getX(), record.getX()) == 0 && Double.compare(getY(), record.getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    private double sumOfSquare(double x ,double y) {
        return Math.pow(x, 2.0) + Math.pow(y, 2.0);
    }
    @Override
    public int compareTo(Record otherRecord) {
        double thisSum = sumOfSquare(this.getX(), this.getY());
        double otherSum = sumOfSquare(otherRecord.getX(), otherRecord.getY());

        if (thisSum > otherSum) {
            return 1;
        } else if (thisSum < otherSum) {
            return -1;
        } else {
            return 0;
        }
    }


}
