package back;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class GameOfLife3D implements GameOfLife {
	
    private State[][][] board;
    private Rule rule;
    private int size;
    
    public GameOfLife3D(int size, Rule rule, Collection<Cell> cells) throws Exception
    {
    	if(rule == Rule.CLASSIC || rule == Rule.SLOW || rule == Rule.SYMMETRIC)
    		this.rule = rule;
    	else
    		throw new Exception("Rule " +rule +" not applicable for 3D Game of Life");
    	this.size = size;
    	board = new State[size][size][size];
    	for(int i=0; i < size; i++)
    		for(int j=0; j < size; j++)
    			for(int k=0; k < size; k++)
    				board[i][j][k] = State.DEAD;
    	for(Cell c : cells)
    		board[c.getX()][c.getY()][c.getZ()] = c.getState();
    }
    
    public GameOfLife3D(int size, Rule rule, State[][][] board)
    {
    	this.rule = rule;
    	this.size = size;
    	this.board = new State[size][size][size];
    	for(int i=0; i < size; i++)
    		for(int j=0; j < size; j++)
    			for(int k=0; k < size; k++)
    				this.board[i][j][k] = board[i][j][k];
    }
    
	@Override
	public int countAliveCells() {
		int aliveCells = 0;
    	for(int i=0; i < size; i++)
    		for(int j=0; j < size; j++)
    			for(int k=0; k < size; k++)
    				if(board[i][j][k] == State.ALIVE)
    					aliveCells++;
    	return aliveCells;
	}

	@Override
	public boolean hasAliveBorderCells() {
    	for(int i=0; i < size; i++)
    		for(int j=0; j < size; j++)
    			for(int k=0; k < size; k++)
					if((i == 0 || j == 0 || k == 0 || i == size-1 || j == size-1 || k == size-1) && board[i][j][k] == State.ALIVE)
						return true;
    	return false;
	}

	@Override
	public int countAliveNeighbors(Integer... coordinates) throws Exception {
		if(coordinates.length != 3)
			throw new Exception("Coordinates must contain three values only!");
		int x = coordinates[0];
		int y = coordinates[1];
		int z = coordinates[2];
		if(x < 0 || y < 0 || z < 0 || x >= size || y >= size || z >= size)
			throw new Exception("Coordinates must be within board limits!");
		
		int aliveNeighbors = countAliveNeighborsInZ(false, x, y, z);
		if(z > 0)
			aliveNeighbors += countAliveNeighborsInZ(true, x, y, z-1);
		if(z < size-1)
			aliveNeighbors += countAliveNeighborsInZ(true, x, y, z+1);
		return aliveNeighbors;
	}
	
	// Counts neighbors of a point in a single Z plane.
	// Set countCenter to true when (x,y,z) should be counted as a neighbor
	private int countAliveNeighborsInZ(boolean countCenter, int x, int y, int z)
	{
		int aliveNeighbors = 0;
		// NW
		if(x > 0 && y < size-1)
		{
			if(board[x-1][y+1][z] == State.ALIVE)
				aliveNeighbors++;
		}
		// N
		if(y < size-1)
		{
			if(board[x][y+1][z] == State.ALIVE)
				aliveNeighbors++;
		}
		// NE
		if(x < size-1 && y < size-1)
		{
			if(board[x+1][y+1][z] == State.ALIVE)
				aliveNeighbors++;
		}
		// W
		if(x > 0)
		{
			if(board[x-1][y][z] == State.ALIVE)
				aliveNeighbors++;
		}
		// Center
		if(countCenter && board[x][y][z] == State.ALIVE)
		{
			aliveNeighbors++;
		}
		// E
		if(x < size-1)
		{
			if(board[x+1][y][z] == State.ALIVE)
				aliveNeighbors++;
		}
		// SW
		if(x > 0 && y > 0)
		{
			if(board[x-1][y-1][z] == State.ALIVE)
				aliveNeighbors++;
		}
		// S
		if(y > 0)
		{
			if(board[x][y-1][z] == State.ALIVE)
				aliveNeighbors++;
		}
		// SE
		if(x < size-1 && y > 0)
		{
			if(board[x+1][y-1][z] == State.ALIVE)
				aliveNeighbors++;
		}
		return aliveNeighbors;
	}

	@Override
	public GameOfLife next() {
		GameOfLife3D prevState = new GameOfLife3D(size, rule, board);
		int[][][] neighbors = new int[size][size][size];
		try
		{
	    	for(int i=0; i < size; i++)
	    		for(int j=0; j < size; j++)
	    			for(int k=0; k < size; k++)
	    				neighbors[i][j][k] = countAliveNeighbors(i, j, k);
			switch(rule)
			{
				case CLASSIC:
				    	for(int i=0; i < size; i++)
				    	{
				    		for(int j=0; j < size; j++)
				    		{
				    			for(int k=0; k < size; k++)
				    			{
				    				// Based on Rule 5766
					    			if(board[i][j][k] == State.ALIVE)
					    			{
					    				if(neighbors[i][j][k] < 5 || neighbors[i][j][k] > 7)
					    					board[i][j][k] = State.DEAD;
					    			}
					    			else if(neighbors[i][j][k] == 6)
					    				board[i][j][k] = State.ALIVE;
				    			}
				    		}
				    	}
				    	break;
				case SLOW:
			    	for(int i=0; i < size; i++)
			    	{
			    		for(int j=0; j < size; j++)
			    		{
			    			for(int k=0; k < size; k++)
			    			{
			    				// Based on Rule 4555
			    				if(board[i][j][k] == State.ALIVE)
				    			{
				    				if(neighbors[i][j][k] != 4 && neighbors[i][j][k] != 5)
				    					board[i][j][k] = State.DEAD;
				    			}
				    			else if(neighbors[i][j][k] == 5)
				    				board[i][j][k] = State.ALIVE;
			    			}
			    		}
			    	}
			    	break;
			    case SYMMETRIC:
			    	for(int i=0; i < size; i++)
			    	{
			    		for(int j=0; j < size; j++)
			    		{
			    			for(int k=0; k < size; k++)
			    			{
			    				// Based on Rule 3333
			    				if(board[i][j][k] == State.ALIVE)
				    			{
				    				if(neighbors[i][j][k] != 3)
				    					board[i][j][k] = State.DEAD;
				    			}
				    			else if(neighbors[i][j][k] == 3)
				    				board[i][j][k] = State.ALIVE;
			    			}
			    		}
			    	}
			    	break;
			    default:
					throw new Exception("Rule " +rule +" not applicable for 3D Game of Life");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	return prevState;
	}

	@Override
	public Collection<Cell> getStatus() {
		List<Cell> list = new LinkedList<>();
    	for(int i=0; i < size; i++)
    	{
    		for(int j=0; j < size; j++)
    		{
    			for(int k=0; k < size; k++)
    			{
    				try
        			{
    					list.add(new Cell(board[i][j][k], i, j, k));
    				}
    				catch (Exception e)
    				{
    					e.printStackTrace();
    					return null;
    				}
    			}
    		}
    	}
    	return list;
	}
	
	public void printBoard()
	{
    	for(int j=size-1; j >= 0; j--)
    	{
    		for(int k=0; k < size; k++)
    		{
        		for(int i=0; i < size; i++)
        		{
        			if(board[i][j][k] == State.ALIVE)
        				System.out.print("+");
        			else
        				System.out.print("-");
        		}
        		System.out.print("\t");
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
		GameOfLife3D other = (GameOfLife3D) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (rule != other.rule)
			return false;
		if (size != other.size)
			return false;
		return true;
	}
	
}
