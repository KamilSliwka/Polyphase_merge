package org.example;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestRecordComparable {
    @Test
    public void testCompareToGreater() {
        Record record1 = new Record(3.0, 4.0); // suma kwadratów to 25
        Record record2 = new Record(1.0, 1.0); // suma kwadratów to 2

        assertEquals(1, record1.compareTo(record2), "record1 powinno być większe niż record2");
    }

    @Test
    public void testCompareToLesser() {
        Record record1 = new Record(1.0, 1.0); // suma kwadratów to 2
        Record record2 = new Record(3.0, 4.0); // suma kwadratów to 25

        assertEquals(-1, record1.compareTo(record2), "record1 powinno być mniejsze niż record2");
    }

    @Test
    public void testCompareToEqual() {
        Record record1 = new Record(3.0, 4.0); // suma kwadratów to 25
        Record record2 = new Record(4.0, 3.0); // suma kwadratów to 25

        assertEquals(0, record1.compareTo(record2), "record1 powinno być równe record2");
    }
}
