package back;

import java.util.Arrays;

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
    
    public int getDimension() {
        return coordinates.length;
    }

    public State getState() {
        return state;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coordinates);
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (!Arrays.equals(coordinates, other.coordinates))
			return false;
		return true;
	}
}
