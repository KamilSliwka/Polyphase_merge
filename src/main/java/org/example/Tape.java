package org.example;

public class Tape {
    private BlockBufferedFile blockBufferedFile;
    private int series;

    public Tape(BlockBufferedFile blockBufferedFile) {
        this.blockBufferedFile = blockBufferedFile;
        this.series = 0;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public void increaseSeries() {
        series++;
    }

    public BlockBufferedFile getBlockBufferedFile() {
        return blockBufferedFile;
    }

    public void setBlockBufferedFile(BlockBufferedFile blockBufferedFile) {
        this.blockBufferedFile = blockBufferedFile;
    }
}
