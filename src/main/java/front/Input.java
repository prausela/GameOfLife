package front;

import back.Cell;

public class Input {
    private boolean singleOutputFile;
    private Long maxIters;
    private boolean randomize;
    private short alivePercentage;
    private int seed;
    private Integer x;
    private Integer y;
    private Integer z;
    private Iterable<Cell> cells;

    public int getSeed() {
        return seed;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getZ() {
        return z;
    }

    public Iterable<Cell> getCells() {
        return cells;
    }

    public Long getMaxIters() {
        return maxIters;
    }

    public short getAlivePercentage() {
        return alivePercentage;
    }
}
