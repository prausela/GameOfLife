package front;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import back.Cell;

public class Output {
	public static String OUTPUT_DIR = "output";
	public static int ITERATION_LIMIT = 5;
    
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
        		System.out.println(t +"\t" +c.getX() +"\t" +c.getY() +"\t" +c.getZ() +"\t" +c.getState().ordinal());
    	}
    }
    
    public static void outputToFile(int t, Iterable<Cell> cells)
    {
    	int fileNumber = t / ITERATION_LIMIT;
    	String outputFileName = "output/t" +fileNumber + ".txt";
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
        	writer.write("t" +t +"\n");
        	if(cells.iterator().next().getDimension() == 2)
        	{
            	for(Cell c : cells)
            		writer.write(c.getX() +"\t" +c.getY() +"\t" +c.getState().ordinal() +"\n");
        	}
        	else
        	{
            	for(Cell c : cells)
            		writer.write(c.getX() +"\t" +c.getY() +"\t" +c.getZ() +"\t" +c.getState().ordinal() +"\n");
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
}
