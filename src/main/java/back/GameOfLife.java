package back;
import java.util.Collection;

public interface GameOfLife {
	int countAliveCells();
	boolean hasAliveBorderCells();
	int countAliveNeighbors(Integer... coordinates) throws Exception;
	GameOfLife next();
	Collection<Cell> getStatus();
}
