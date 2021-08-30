package back;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class GameOfLife2D implements GameOfLife {
    private State[][] board;
    private Rule rule;
    private int size;

    public GameOfLife2D(int size, Rule rule, Collection<Cell> cells) throws Exception
    {
    	if(rule == Rule.CLASSIC || rule == Rule.REPLICATOR || rule ==Rule.LONGLIFE)
    		this.rule = rule;
    	else
    		throw new Exception("Rule " +rule +" not applicable for 2D Game of Life");
    	this.size = size;
    	board = new State[size][size];
    	for(int i=0; i < size; i++)
    		for(int j=0; j < size; j++)
    			board[i][j] = State.DEAD;
    	for(Cell c : cells)
    		board[c.getX()][c.getY()] = c.getState();
    }
    
    public GameOfLife2D(int size, Rule rule, State[][] board) throws Exception
    {
    	if(rule == Rule.CLASSIC || rule == Rule.REPLICATOR || rule == Rule.LONGLIFE)
    		this.rule = rule;
    	else
    		throw new Exception("Rule " +rule +" not applicable for 2D Game of Life");
    	this.size = size;
    	this.board = new State[size][size];
    	for(int i=0; i < size; i++)
    		for(int j=0; j < size; j++)
    			this.board[i][j] = board[i][j];
    }
    
	@Override
	public int countAliveCells() {
		int aliveCells = 0;
    	for(int i=0; i < size; i++)
    		for(int j=0; j < size; j++)
				if(board[i][j] == State.ALIVE)
					aliveCells++;
    	return aliveCells;
	}
	
	@Override
	public boolean hasAliveBorderCells() {
    	for(int i=0; i < size; i++)
    		for(int j=0; j < size; j++)
				if((i == 0 || j == 0 || i == size-1 || j == size-1) && board[i][j] == State.ALIVE)
					return true;
    	return false;
	}

	@Override
	public int countAliveNeighbors(Integer... coordinates) throws Exception {
		if(coordinates.length != 2)
			throw new Exception("Coordinates must contain two values only!");
		int x = coordinates[0];
		int y = coordinates[1];
		if(x < 0 || y < 0 || x >= size || y >= size)
			throw new Exception("Coordinates must be within board limits!");
		
		int aliveNeighbors = 0;
		// NW
		if(x > 0 && y < size-1)
		{
			if(board[x-1][y+1] == State.ALIVE)
				aliveNeighbors++;
		}
		// N
		if(y < size-1)
		{
			if(board[x][y+1] == State.ALIVE)
				aliveNeighbors++;
		}
		// NE
		if(x < size-1 && y < size-1)
		{
			if(board[x+1][y+1] == State.ALIVE)
				aliveNeighbors++;
		}
		// W
		if(x > 0)
		{
			if(board[x-1][y] == State.ALIVE)
				aliveNeighbors++;
		}
		// E
		if(x < size-1)
		{
			if(board[x+1][y] == State.ALIVE)
				aliveNeighbors++;
		}
		// SW
		if(x > 0 && y > 0)
		{
			if(board[x-1][y-1] == State.ALIVE)
				aliveNeighbors++;
		}
		// S
		if(y > 0)
		{
			if(board[x][y-1] == State.ALIVE)
				aliveNeighbors++;
		}
		// SE
		if(x < size-1 && y > 0)
		{
			if(board[x+1][y-1] == State.ALIVE)
				aliveNeighbors++;
		}
		return aliveNeighbors;
	}

	@Override
	public GameOfLife next() {
		GameOfLife2D prevState;
		try
		{
			prevState = new GameOfLife2D(size, rule, board);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		int[][] neighbors = new int[size][size];
		try
		{
	    	for(int i=0; i < size; i++)
	    		for(int j=0; j < size; j++)
	    			neighbors[i][j] = countAliveNeighbors(i, j);
			switch(rule)
			{
				case CLASSIC:
				    	for(int i=0; i < size; i++)
				    	{
				    		for(int j=0; j < size; j++)
				    		{
				    			if(board[i][j] == State.ALIVE)
				    			{
				    				if(neighbors[i][j] != 2 && neighbors[i][j] != 3)
				    					board[i][j] = State.DEAD;
				    			}
				    			else if(neighbors[i][j] == 3)
				    				board[i][j] = State.ALIVE;
				    		}
				    	}
				    	break;
				case LONGLIFE:
			    	for(int i=0; i < size; i++)
			    	{
			    		for(int j=0; j < size; j++)
			    		{
			    			if(board[i][j] == State.ALIVE)
			    			{
			    				if(neighbors[i][j] != 5)
			    					board[i][j] = State.DEAD;
			    			}
			    			else if(neighbors[i][j] == 3 || neighbors[i][j] == 4 || neighbors[i][j] == 5)
			    				board[i][j] = State.ALIVE;
			    		}
			    	}
			    	break;
			    case REPLICATOR:
			    	for(int i=0; i < size; i++)
			    	{
			    		for(int j=0; j < size; j++)
			    		{
			    			if(board[i][j] == State.ALIVE)
			    			{
			    				if(neighbors[i][j] % 2 == 0)
			    					board[i][j] = State.DEAD;
			    			}
			    			else if(neighbors[i][j] % 2 != 0)
			    				board[i][j] = State.ALIVE;
			    		}
			    	}
			    	break;
			    default:
			    	throw new Exception("Rule " +rule +" not applicable for 2D Game of Life");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	return prevState;
	}

	@Override
	public Collection<Double> getCellsRadius() {
    	Collection<Double> cellsRadius = new LinkedList<>();
		for(int i = 0; i < size; i++) {
			for( int j = 0; j < size; j++ ) {
				if( board[i][j].equals(State.ALIVE)) {
					cellsRadius.add(Math.sqrt( (i- size/(float)2)*(i- size/(float)2) + (j- size/(float)2)*(j- size/(float)2)));
				}
			}
		}
		return cellsRadius;
	}

	@Override
	public Collection<Cell> getStatus()
	{
		List<Cell> list = new LinkedList<>();
    	for(int i=0; i < size; i++)
    	{
    		for(int j=0; j < size; j++)
    		{
				try
    			{
					list.add(new Cell(board[i][j], i, j));
				}
				catch (Exception e)
				{
					e.printStackTrace();
					return null;
				}
    		}
    	}
    	return list;
	}
	
	@Override
	public void printBoard()
	{
    	for(int j=size-1; j >= 0; j--)
    	{
    		for(int i=0; i < size; i++)
    		{
    			if(board[i][j] == State.ALIVE)
    				System.out.print("+");
    			else
    				System.out.print("-");
    		}
    		System.out.println();
    	}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		result = prime * result + ((rule == null) ? 0 : rule.hashCode());
		result = prime * result + size;
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
		GameOfLife2D other = (GameOfLife2D) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (rule != other.rule)
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	@Override
	public int getBoardSize() {
		return size;
	}
	
	@Override
	public int getDimensions() {
		return 2;
	}
}
