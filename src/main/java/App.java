import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import back.*;
import front.Input;
import front.Output;
import front.Parser;

public class App {

	public static void main(String[] args) throws Exception
	{
		Input input = Parser.ParseJSON("input.json");
		Output.resetFolder(Output.OUTPUT_DIR);
		int t = 0;
		GameOfLife game = null;
		GameOfLife backup = null;

		if(input.getDimensions() == 2)
			game = new GameOfLife2D(input.getBoardSize(), input.getRule(), input.getCells());
		else if(input.getDimensions() == 3)
			game = new GameOfLife3D(input.getBoardSize(), input.getRule(), input.getCells());
		else
		{
			System.out.println("Dimensions must be 2 or 3");
			return;
		}

		// Scalars preparations
		Map<Integer, Integer> massMap = new HashMap<>();

		// Run simulation
		if(input.isTest())
		{
			int regionSize;
			if(input.getBoardSize() > 7)
				regionSize = 7;
			else
				regionSize = input.getBoardSize()/4;

			for(int i = 0; i < 100; i++)
			{
				System.out.println("Seed " +(i+1));
				int seed = (int) (Math.random() * Integer.MAX_VALUE);
				Collection<Cell> cells;
				for(int percentage = 5; percentage <= 100; percentage += 5)
				{
					if(input.getDimensions() == 2)
					{
						cells = Generator.generate2D(seed, input.getBoardSize(), regionSize, percentage);
						game = new GameOfLife2D(input.getBoardSize(), input.getRule(), cells);
					}
					else
					{
						cells = Generator.generate3D(seed, input.getBoardSize(), regionSize, percentage);
						game = new GameOfLife3D(input.getBoardSize(), input.getRule(), cells);
					}
					do
					{

						int aliveCells = game.countAliveCells();

						if( aliveCells > 0 ) {
							Output.outputGameCellsState(seed, i, percentage, t, aliveCells, Collections.max(game.getCellsRadius()));
						} else {
							Output.outputGameCellsState(seed, i, percentage, t, aliveCells, 0);
						}
						massMap.put(t, game.countAliveCells());
						backup = game.next();
						t++;
					}while(!backup.hasAliveBorderCells() && backup.countAliveCells() > 0 && !game.equals(backup) && t < input.getMaxIterations());
					CauseOfDeath cause = null;
					if(backup.countAliveCells() == 0)
						cause = CauseOfDeath.LONELINESS;
					else if(backup.hasAliveBorderCells())
						cause = CauseOfDeath.HIT_WALL;
					else if(t == input.getMaxIterations() || game.equals(backup))
						cause = CauseOfDeath.MAX_T;
					Output.outputScalars(seed, t, massMap, input, percentage, cause);
					massMap.clear();
					t = 0;
				}
			}
			System.out.println("Finished!");
		}
		else
		{
			do
			{
				System.out.println("\nt = " +t);
				//game.printBoard();
				massMap.put(t, game.countAliveCells());
				Output.outputCurrentScalars(t, game);
				Output.outputToFile(t, game.getStatus());
				backup = game.next();
				t++;
			}while(!backup.hasAliveBorderCells() && backup.countAliveCells() > 0 && !game.equals(backup) && t < input.getMaxIterations());
			
			// Scalar output
			int massDiff = massMap.get(t-1) - massMap.get(0);
			double massDiffPerc = 100.0*massDiff/massMap.get(0);
			double livingPerc = 100.0*massMap.get(t-1)/Math.pow(input.getBoardSize(), input.getDimensions());
			System.out.println("This simulation lasted " +t +" iterations");
			System.out.println("This simulation ended with " +livingPerc +"% of cells alive");
			if(massDiff > 0)
				System.out.println("This simulation had a mass change of +" +massDiff +" cells (+" +massDiffPerc +"%)");
			else
				System.out.println("This simulation had a mass change of " +massDiff +" cells (" +massDiffPerc +"%)");
		}
	}
}
