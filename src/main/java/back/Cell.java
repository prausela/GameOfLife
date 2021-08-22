package back;

public class Cell {
    private final Integer[] coordinates;
    private final State state;

    public Cell(Integer[] coordinates, State state){
        this.coordinates = coordinates;
        this.state = state;
    }

    public Integer[] getCoordinates() {
        return coordinates;
    }

    public State getState() {
        return state;
    }
}
