package back;

import java.util.LinkedList;
import java.util.List;

public class GameOfLife2D implements GameOfLife {
    private State[][] board;
    int height;
    int width;

    public GameOfLife2D(int width, int height, Iterable<Cell> cells)
    {
    	this.width = width;
    	this.height = height;
    	board = new State[width][height];
    	for(int i=0; i < width; i++)
    		for(int j=0; j < height; j++)
    			board[i][j] = State.DEAD;
    	for(Cell c : cells)
    		board[c.getX()][c.getY()] = c.getState();
    }
    
    public GameOfLife2D(int width, int height, State[][] board)
    {
    	this.width = width;
    	this.height = height;
    	this.board = new State[width][height];
    	for(int i=0; i < width; i++)
    		for(int j=0; j < height; j++)
    			this.board[i][j] = board[i][j];
    }
    
    public State[][] getBoard()
    {
    	return board;
    }

	@Override
	public int countAliveNeighbors(Integer... coordinates) throws Exception {
		if(coordinates.length != 2)
			throw new Exception("Coordinates must contain two values only!");
		int x = coordinates[0];
		int y = coordinates[1];
		if(x < 0 || y < 0 || x >= width || y >= height)
			throw new Exception("Coordinates must be within board limits!");
		
		int aliveNeighbors = 0;
		// NW
		if(x > 0 && y < height-1)
		{
			if(board[x-1][y+1] == State.ALIVE)
				aliveNeighbors++;
		}
		// N
		if(y < height-1)
		{
			if(board[x][y+1] == State.ALIVE)
				aliveNeighbors++;
		}
		// NE
		if(x < width-1 && y < height-1)
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
		if(x < width-1)
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
		if(x < width-1 && y > 0)
		{
			if(board[x+1][y-1] == State.ALIVE)
				aliveNeighbors++;
		}
		return aliveNeighbors;
	}

	@Override
	public GameOfLife2D next() {
		GameOfLife2D prevState = new GameOfLife2D(width, height, board);
		int[][] neighbors = new int[width][height];
		try
		{
	    	for(int i=0; i < width; i++)
	    		for(int j=0; j < height; j++)
	    			neighbors[i][j] = countAliveNeighbors(i, j);
	    	
	    	for(int i=0; i < width; i++)
	    	{
	    		for(int j=0; j < height; j++)
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
    	for(int i=0; i < width; i++)
    	{
    		for(int j=0; j < height; j++)
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
    	for(int j=height-1; j >= 0; j--)
    	{
    		for(int i=0; i < width; i++)
    		{
    			if(board[i][j] == State.ALIVE)
    				System.out.print("+");
    			else
    				System.out.print("-");
    		}
    		System.out.println();
    	}
	}

}
