import back.Cell;
import back.GameOfLife2D;
import back.Generator;
import back.Rule;
import front.Output;

public class App {
	
	public static void main(String[] args) throws Exception
	{
		int seed = 227;
		int boardSize = 20;
		int regionSize = 4;
		int percentage = 50;
		Rule rule = Rule.CLASSIC;
		Output.resetFolder(Output.OUTPUT_DIR);
		Iterable<Cell> set = Generator.generate2D(seed, boardSize, regionSize, percentage);
		GameOfLife2D game = new GameOfLife2D(boardSize, rule, set);
		GameOfLife2D backup = null;
		int t = 0;
		
		// Run simulation
		do
		{
			System.out.println("\nt = " +t);
			game.printBoard();
			Output.outputToFile(t, game.getStatus());
			backup = game.next();
			t++;
		}while(!backup.hasAliveBorderCells() && backup.countAliveCells() > 0 && !game.equals(backup));
	}
}
