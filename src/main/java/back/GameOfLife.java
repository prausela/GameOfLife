package back;
import java.util.Collection;

public interface GameOfLife {
	int countAliveCells();
	boolean hasAliveBorderCells();
	int countAliveNeighbors(Integer... coordinates) throws Exception;
	int getBoardSize();
	int getDimensions();
	GameOfLife next();
	Collection<Cell> getStatus();
	void printBoard();
	double getMaxCellsRadius();
}
