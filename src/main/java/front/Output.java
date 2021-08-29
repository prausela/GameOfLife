package front;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import back.CauseOfDeath;
import back.Cell;
import back.GameOfLife;

public class Output {
	public static String OUTPUT_DIR = "output";
    
    public static void outputToConsole(int t, Iterable<Cell> cells)
    {
    	if(cells.iterator().next().getDimension() == 2)
    	{
    		System.out.println("t\tx\ty\tstatus");
        	for(Cell c : cells)
        		System.out.println(t +"\t" +c.getX() +"\t" +c.getY() +"\t" +c.getState().ordinal());
    	}
    		
    	else
    	{
    		System.out.println("t\tx\ty\tz\tstatus");
        	for(Cell c : cells)
        		System.out.println(t +"\t" +c.getX() +"\t" +c.getY() +"\t" +c.getZ() +"\t" + c.getState().ordinal());
    	}
    }

    public static void outputScalars(int seed, int iterations, Map<Integer, Integer> massMap, Input input, int generatorAlivePercentage, CauseOfDeath cause) {
		int massDiff = massMap.get(iterations-1) - massMap.get(0);
		double massDiffPerc = 100.0*massDiff/massMap.get(0);
		double livingPerc = 100.0*massMap.get(iterations-1)/Math.pow(input.getBoardSize(), input.getDimensions());
		String outputFileName = "output/scalars.csv";
		File file = new File(outputFileName);
		try
		{
			if(file.createNewFile())
			{
				FileWriter writer = new FileWriter(outputFileName, true);
				writer.write("seed;genAlivePerc;iterations;massDiff;massDiffPerc;livingPerc;causeOfDeath\n");
				writer.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}

		try (FileWriter writer = new FileWriter(outputFileName, true))
		{
			writer.write(seed +";" +generatorAlivePercentage +";" + iterations +";" + massDiff + ";" +massDiffPerc + ";" + livingPerc + ";" +cause.ordinal() +"\n");
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void outputGameCellsState(int seed, int iteration, int genAlivePerc, int t, int aliveCells, double maxRadius) {
		String outputFileName = "output/gameCellsState_" + genAlivePerc + ".csv";
		File file = new File(outputFileName);
		try
		{
			if(file.createNewFile())
			{
				FileWriter writer = new FileWriter(outputFileName, true);
				writer.write("seed;t;aliveCells;maxRadius\n");
				writer.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}

		try (FileWriter writer = new FileWriter(outputFileName, true))
		{
			writer.write(seed +";" + t +";" + aliveCells +";" + maxRadius +"\n");
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

    public static void outputToFile(int t, Collection<Cell> cells)
    {
    	String outputFileName = "output/evolution.txt";
    	File file = new File(outputFileName);
    	try
    	{
			file.createNewFile();
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
			return;
		}
    	
        try (FileWriter writer = new FileWriter(outputFileName, true))
        {
        	writer.write(cells.size() +"\n\n");
        	if(cells.iterator().next().getDimension() == 2)
        	{
            	for(Cell c : cells)
            		writer.write(c.getX() +"\t" +c.getY() +"\t" + (c.getState().ordinal() == 0 ? 1:0) +"\n");
        	}
        	else
        	{
            	for(Cell c : cells)
            		writer.write(c.getX() +"\t" +c.getY() +"\t" +c.getZ() +"\t" + (c.getState().ordinal() == 0 ? 1:0) +"\n");
        	}
        	writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void resetFolder(String folderName)
    {
    	File folder = new File(folderName);
        File[] files = folder.listFiles();
        if(files!=null)
        {
            for(File f: files)
                f.delete();
        }
        folder.delete();
        folder.mkdir();        
    }
    
    public static void outputCurrentScalars(int t, GameOfLife game)
    {
    	String outputFileName = "output/vars.csv";
    	File file = new File(outputFileName);
    	try
    	{
			if(file.createNewFile())
			{
				FileWriter writer = new FileWriter(outputFileName, true);
				writer.write("t, masa, porcentaje\n");
	        	writer.close();
			}
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
			return;
		}
    	double livingPerc = 100.0*game.countAliveCells()/Math.pow(game.getBoardSize(), game.getDimensions());
        try (FileWriter writer = new FileWriter(outputFileName, true))
        {
        	writer.write(t +"," +game.countAliveCells() +"," +livingPerc +"\n");
        	writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
