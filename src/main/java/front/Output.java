package front;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import back.CauseOfDeath;
import back.Cell;

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

	
	public static void outputToFile(int t, Collection<Cell> cells, int boardSize, boolean postProcess)
	{
		outputToFile(t, cells, boardSize, "output/evolution.txt", postProcess);
	}
	
    public static void outputToFile(int t, Collection<Cell> cells, int boardSize, String outputFileName, boolean postProcess)
    {
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
        	double boardRadius, radius;
        	if(cells.iterator().next().getDimension() == 2)
        	{
        		boardRadius = Math.sqrt(Math.pow((boardSize-1.0)/2.0, 2) * 2);
            	for(Cell c : cells)
            	{
            		radius = getRadius(c, boardSize)/boardRadius;
            		writer.write(c.getX() +"\t" +c.getY());
            		if(postProcess)
            			writer.write("\t" + (1-c.getState().ordinal()) +"\t" +radius +"\t" +(1-radius) +"\n");
            		else
            			writer.write("\t" + (c.getState().ordinal()) +"\n");
            	}
        	}
        	else
        	{
        		boardRadius = Math.sqrt(Math.pow((boardSize-1.0)/2.0, 2) * 3);
            	for(Cell c : cells)
            	{
            		radius = getRadius(c, boardSize)/boardRadius;
            		writer.write(c.getX() +"\t" +c.getY() +"\t" +c.getZ());
            		if(postProcess)
            			writer.write("\t" + (1-c.getState().ordinal()) +"\t" +radius +"\t" +(1-radius) +"\n");
            		else
            			writer.write("\t" + (c.getState().ordinal()) +"\n");
            	}
            		
        	}
        	writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    

    public static void outputRadiusToFile(int t, Collection<Cell> cells, int boardSize, String outputFileName)
    {
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
    	double boardRadius;
        try (FileWriter writer = new FileWriter(outputFileName, true))
        {
        	writer.write(cells.size() +"\n\n");
        	if(cells.iterator().next().getDimension() == 2)
        	{
        		boardRadius = Math.sqrt(Math.pow((boardSize-1.0)/2.0, 2) * 2);
            	for(Cell c : cells)
            		writer.write(getRadius(c, boardSize)/boardRadius +"\n");
        	}
        	else
        	{
        		boardRadius = Math.sqrt(Math.pow((boardSize-1.0)/2.0, 2) * 3);
            	for(Cell c : cells)
            		writer.write(getRadius(c, boardSize)/boardRadius +"\n");
        	}
        	writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private static double getRadius(Cell c, int boardSize)
    {
    	double x = c.getX() + 0.5;
    	double y = c.getY() + 0.5;
    	if(c.getDimension() == 2)
    		return Math.sqrt(Math.pow(x - boardSize/2.0, 2) + Math.pow(y - boardSize/2.0, 2));
    	else
    	{
    		double z = c.getZ() + 0.5;
    		return Math.sqrt(Math.pow(x - boardSize/2.0, 2) + Math.pow(y - boardSize/2.0, 2) + Math.pow(z - boardSize/2.0, 2));
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
    
}
