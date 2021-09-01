import java.util.*;

import back.*;
import front.Input;
import front.Output;
import front.Parser;

public class App {

	public static void main(String[] args) throws Exception
	{
		Input input = Parser.ParseJSON("input.json");
		Output.resetFolder(Output.OUTPUT_DIR);
		GameOfLife game;

		if(input.getDimensions() == 2)
			game = new GameOfLife2D(input.getBoardSize(), input.getRule(), input.getCells());
		else if(input.getDimensions() == 3)
			game = new GameOfLife3D(input.getBoardSize(), input.getRule(), input.getCells());
		else
		{
			System.out.println("Dimensions must be 2 or 3");
			return;
		}

		// Run simulation
		if(input.isTest())
			runTest(input);
		else
			runGame(game, input);
	}

	private static void runTest( Input input ) throws Exception {
		int regionSize;
		GameOfLife backup;
		GameOfLife game;
		int simulations = 100;
		int seed;
		int t = 0;

		if(input.getBoardSize() > 7)
			regionSize = 7;
		else
			regionSize = input.getBoardSize()/2;
		
		// Run simulations
		for(int i = 0; i < simulations; i++)
		{
			System.out.println("Testing with seed " +(i+1));
			seed = (int) (Math.random() * Integer.MAX_VALUE);
			Collection<Cell> cells;
			Map<Integer, Integer> massMap = new HashMap<>();
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
				if(input.isAutoPostProcess())
				{
					do
					{
						int aliveCells = game.countAliveCells();
						if( aliveCells > 0 )
							Output.outputGameCellsState(seed, i, percentage, t, aliveCells, game.getMaxCellsRadius()); 
				        else
				        	Output.outputGameCellsState(seed, i, percentage, t, aliveCells, 0);
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
				else
				{
					do
					{
						Output.outputToFile(t, game.getStatus(), game.getBoardSize(), "output/evolution-"+percentage+"-"+i+".txt", false);
						backup = game.next();
						t++;
					}while(!backup.hasAliveBorderCells() && backup.countAliveCells() > 0 && !game.equals(backup) && t < input.getMaxIterations());
					t = 0;
				}
			}
		}
		System.out.println("Finished!");
	}

	private static void runGame( GameOfLife game, Input input ) {
		int t = 0;
		GameOfLife backup;
		Map<Integer, Integer> massMap = new HashMap<>();

		do
		{
			System.out.println("t = " +t);
			massMap.put(t, game.countAliveCells());
			Output.outputToFile(t, game.getStatus(), game.getBoardSize(), input.isAutoPostProcess());
			backup = game.next();
			t++;
		}while(!backup.hasAliveBorderCells() && backup.countAliveCells() > 0 && !game.equals(backup) && t < input.getMaxIterations());

		// Post-processing
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
