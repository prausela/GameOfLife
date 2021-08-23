package back;

public interface GameOfLife {
	int countAliveCells();
	boolean hasAliveBorderCells();
	int countAliveNeighbors(Integer... coordinates) throws Exception;
	GameOfLife next();
	Iterable<Cell> getStatus();
}
