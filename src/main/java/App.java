import java.util.Collection;
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
		if( input.isTest() ) {
			int regionSize;
			if( input.getBoardSize() > 7 )
				regionSize = 7;
			else
				regionSize = input.getBoardSize()/2;
			int percentage;

			for( int i = 0; i < 100; i++ ) {
				int seed = (int) (Math.random() * Integer.MAX_VALUE);
				Collection<Cell> cells;
				for( percentage = 10; percentage <= 100; percentage += 5) {
					if(input.getDimensions() == 2) {
						cells = Generator.generate2D(seed, input.getBoardSize(), regionSize, percentage);
						game = new GameOfLife2D(input.getBoardSize(), input.getRule(), cells);
					} else {
						cells = Generator.generate3D(seed, input.getBoardSize(), regionSize, percentage);
						game = new GameOfLife3D(input.getBoardSize(), input.getRule(), cells);
					}
					do
					{
						massMap.put(t, game.countAliveCells());
						backup = game.next();
						t++;
					}while(!backup.hasAliveBorderCells() && backup.countAliveCells() > 0 && !game.equals(backup) && t < input.getMaxIterations());
					Output.outputScalars(t, massMap, input, percentage);
					massMap.clear();
					t = 0;
				}
			}
		} else {
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
