package back;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Generator {
    
	public static Collection<Cell> generate2D(int seed, int boardSize, int regionSize, int percentage) throws Exception
    {
		if(percentage <= 0 || percentage > 100)
			throw new Exception("Percentage value must be between 1 and 100");
		if(regionSize > boardSize)
			throw new Exception("regionSize must be smaller than boardSize");
		Random random = new Random(seed);
		int aliveCells = (int) (regionSize*regionSize * percentage/100.0);
		int boardCenter = boardSize/2;
		int min = boardCenter - regionSize/2;
		int max;
		if(regionSize % 2 == 0)
			max = boardCenter + regionSize/2 - 1;
		else
			max = boardCenter + regionSize/2;		
		
		Set<Cell> cells = new HashSet<>();
		int x,y;
		for(int i=0; i < aliveCells; i++)
		{
			do
			{
				x = min + random.nextInt(max-min+1);
				y = min + random.nextInt(max-min+1);
			}while(cells.contains(new Cell(State.ALIVE, x, y)));
			cells.add(new Cell(State.ALIVE, x, y));
		}
	    return cells;
    }
	
	public static Iterable<Cell> generate2D(int seed, int boardSize, int regionSize) throws Exception
	{
		Random r = new Random();
		return generate2D(seed, boardSize, regionSize, 1 + r.nextInt(100));
	}
	
	public static Iterable<Cell> generate2D(int seed, int boardSize) throws Exception
	{
		Random r = new Random();
		return generate2D(seed, boardSize, Math.min(boardSize/2, 3), 1 + r.nextInt(100));
	}
	
	public static Collection<Cell> generate3D(int seed, int boardSize, int regionSize, int percentage) throws Exception
    {
		if(percentage <= 0 || percentage > 100)
			throw new Exception("Percentage value must be between 1 and 100");
		if(regionSize > boardSize)
			throw new Exception("regionSize must be smaller than boardSize");
		Random random = new Random(seed);
		int aliveCells = (int) ( Math.pow(regionSize, 3) * percentage/100.0 );
		int boardCenter = boardSize/2;
		int min = boardCenter - regionSize/2;
		int max;
		if(regionSize % 2 == 0)
			max = boardCenter + regionSize/2 - 1;
		else
			max = boardCenter + regionSize/2;		
		
		Set<Cell> cells = new HashSet<>();
		int x,y,z;
		for(int i=0; i < aliveCells; i++)
		{
			do
			{
				x = min + random.nextInt(max-min+1);
				y = min + random.nextInt(max-min+1);
				z = min + random.nextInt(max-min+1);
			}while(cells.contains(new Cell(State.ALIVE, x, y, z)));
			cells.add(new Cell(State.ALIVE, x, y, z));
		}
	    return cells;
    }
}
