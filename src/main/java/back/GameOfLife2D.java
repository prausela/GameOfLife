package back;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GameOfLife2D implements GameOfLife {
    private State[][] board;
    private Rule rule;
    private int size;

    public GameOfLife2D(int size, Rule rule, Iterable<Cell> cells)
    {
    	this.rule = rule;
    	this.size = size;
    	board = new State[size][size];
    	for(int i=0; i < size; i++)
    		for(int j=0; j < size; j++)
    			board[i][j] = State.DEAD;
    	for(Cell c : cells)
    		board[c.getX()][c.getY()] = c.getState();
    }
    
    public GameOfLife2D(int size, Rule rule, State[][] board)
    {
    	this.rule = rule;
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
	public GameOfLife2D next() {
		GameOfLife2D prevState = new GameOfLife2D(size, rule, board);
		int[][] neighbors = new int[size][size];
		try
		{
			switch(rule)
			{
				case CLASSIC:
				    	for(int i=0; i < size; i++)
				    		for(int j=0; j < size; j++)
				    			neighbors[i][j] = countAliveNeighbors(i, j);
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
				case HIGHLIFE:
			    	for(int i=0; i < size; i++)
			    		for(int j=0; j < size; j++)
			    			neighbors[i][j] = countAliveNeighbors(i, j);
			    	for(int i=0; i < size; i++)
			    	{
			    		for(int j=0; j < size; j++)
			    		{
			    			if(board[i][j] == State.ALIVE)
			    			{
			    				if(neighbors[i][j] != 2 && neighbors[i][j] != 3)
			    					board[i][j] = State.DEAD;
			    			}
			    			else if(neighbors[i][j] == 3 || neighbors[i][j] == 6)
			    				board[i][j] = State.ALIVE;
			    		}
			    	}
			    	break;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	return prevState;
	}

	@Override
	public Iterable<Cell> getStatus()
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
}
