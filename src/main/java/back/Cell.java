package back;

public class Cell {
    private final Integer[] coordinates;
    private final State state;

    public Cell(State state, Integer... coordinates) throws Exception{
    	if(coordinates.length != 2 && coordinates.length != 3)
    		throw new Exception("Cells need two or three coordinates!");
        this.coordinates = coordinates;
        this.state = state;
    }

    public Integer[] getCoordinates() {
        return coordinates;
    }
    
    public Integer getX() {
    	return coordinates[0];
    }
    
    public Integer getY() {
    	return coordinates[1];
    }
    
    public Integer getZ() {
    	if(coordinates.length == 3)
    		return coordinates[2];
    	return null;
    }

    public State getState() {
        return state;
    }
}
