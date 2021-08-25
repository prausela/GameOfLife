import java.util.Collection;
import back.Cell;
import back.GameOfLife;
import back.GameOfLife2D;
import back.GameOfLife3D;
import back.Generator;
import back.Rule;
import front.Output;

public class App {
	
	public static void main(String[] args) throws Exception
	{
		//for(int seed = 0; seed < 1; seed++)
		//{
			int seed = 9;
			int boardSize = 100;
			int regionSize = 7;
			int percentage = 90;
			int maxIterations = 1000;
			int dimensions = 2;
			Rule rule = Rule.CLASSIC;
			Output.resetFolder(Output.OUTPUT_DIR);
			int t = 0;
			Collection<Cell> set = null;
			GameOfLife game = null;
			GameOfLife backup = null;
			if(dimensions == 2)
			{
				set = Generator.generate2D(seed, boardSize, regionSize, percentage);
				game = new GameOfLife2D(boardSize, rule, set);
			}
			else if(dimensions == 3)
			{
				set = Generator.generate3D(seed, boardSize, regionSize, percentage);
				game = new GameOfLife3D(boardSize, rule, set);
			}
			else
			{
				System.out.println("Dimensions must be 2 or 3");
				return;
			}	
			
			// Run simulation
			do
			{
				//System.out.println("\nt = " +t);
				//game.printBoard();
				Output.outputToFile(t, game.getStatus());
				backup = game.next();
				t++;
			}while(!backup.hasAliveBorderCells() && backup.countAliveCells() > 0 && !game.equals(backup) && t < maxIterations);
			System.out.println("Seed " +seed +" lasted " +t +" iterations on " +rule);
		//}
	}
}
