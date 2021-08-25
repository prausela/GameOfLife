import back.GameOfLife;
import back.GameOfLife2D;
import back.GameOfLife3D;
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
		
		// Run simulation
		do
		{
			System.out.println("\nt = " +t);
			game.printBoard();
			Output.outputToFile(t, game.getStatus());
			backup = game.next();
			t++;
		}while(!backup.hasAliveBorderCells() && backup.countAliveCells() > 0 && !game.equals(backup) && t < input.getMaxIterations());
	}
}
