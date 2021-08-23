package back;

public interface GameOfLife {
	
	int countAliveNeighbors(Integer... coordinates) throws Exception;
	GameOfLife next();
	Iterable<Cell> getStatus();
}
