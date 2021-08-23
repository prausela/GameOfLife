import java.util.LinkedList;
import java.util.List;
import back.Cell;
import back.GameOfLife2D;
import back.State;

public class App {
	
	public static void main(String[] args)
	{
		List<Cell> list = new LinkedList<>();
		try {
			list.add(new Cell(State.ALIVE, 2, 2));
			list.add(new Cell(State.ALIVE, 2, 3));
			list.add(new Cell(State.ALIVE, 3, 2));
			list.add(new Cell(State.ALIVE, 3, 3));
			list.add(new Cell(State.ALIVE, 2, 1));
			GameOfLife2D game = new GameOfLife2D(5, 5, list);
			for(int i=0; i < 4; i++)
			{
				System.out.println("\n\nt = " +i);
				game.printBoard();
				game.next();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
