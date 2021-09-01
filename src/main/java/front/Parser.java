package front;

import java.io.FileReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import back.Cell;
import back.Generator;
import back.Rule;
import back.State;

public class Parser {

	private final static Integer DEFAULT_DIMENSIONS = 2;
	private final static Integer DEFAULT_ITERATIONS = 1000;
	private final static Integer DEFAULT_BOARD_SIZE = 100;
	private final static Boolean DEFAULT_RANDOMIZE = false;
	private final static Boolean DEFAULT_TEST = false;
	private final static Boolean AUTO_POSTPROCESS = false;
	private final static Integer DEFAULT_REGION_SIZE = 7;
	private final static Integer DEFAULT_PERCENTAGE = 50;
	private final static Boolean DEFAULT_UNSPECIFIED_PARTICLE = false;
	private final static Integer DEFAULT_SEED = (int) (Math.random() * Integer.MAX_VALUE);
	
	@SuppressWarnings("unchecked")
    public static Input ParseJSON(String filename)
    {
		Integer dimensions;
		Integer maxIterations;
		Rule rule;
		Integer boardSize;
		boolean randomize;
		boolean test;
		boolean autoPostProcess;
		Integer regionSize;
		Integer alivePercentage;
		Integer seed;
		Collection<Cell> cells = new HashSet<>();
		
    	JSONParser parser = new JSONParser();
		try
		{
			Object obj = parser.parse(new FileReader(filename));
			JSONObject json = (JSONObject) obj;
			dimensions = ((Long) json.getOrDefault("dimensions", DEFAULT_DIMENSIONS)).intValue();
			maxIterations = ((Long) json.getOrDefault("maxIterations", DEFAULT_ITERATIONS)).intValue();
			boardSize = ((Long) json.getOrDefault("boardSize", DEFAULT_BOARD_SIZE)).intValue();
			rule = Rule.valueOf((String) json.getOrDefault("rule", Rule.CLASSIC));
			randomize = (Boolean) json.getOrDefault("randomize", DEFAULT_RANDOMIZE);
			test = (Boolean) json.getOrDefault("test", DEFAULT_TEST);
			autoPostProcess = (Boolean) json.getOrDefault("autoPostProcess", AUTO_POSTPROCESS);
			regionSize = ((Long) json.getOrDefault("regionSize", DEFAULT_REGION_SIZE)).intValue();
			alivePercentage = ((Long) json.getOrDefault("alivePercentage", DEFAULT_PERCENTAGE)).intValue();
			seed = ((Long) json.getOrDefault("seed", DEFAULT_SEED)).intValue();
			JSONArray particles = (JSONArray) json.getOrDefault("particles", null);
			if(!randomize && particles != null)
			{
				Iterator<JSONObject> iterator = particles.iterator();
				while (iterator.hasNext())
				{
					JSONObject cellJson = iterator.next();
					Cell cell = null;
					Boolean status = (Boolean) cellJson.getOrDefault("status", DEFAULT_UNSPECIFIED_PARTICLE);
					State s = State.DEAD;
					if(status)
						s = State.ALIVE;
					
					Integer x = ((Long) cellJson.getOrDefault("x", null)).intValue();
					Integer y = ((Long) cellJson.getOrDefault("y", null)).intValue();
					Integer z = ((Long) cellJson.getOrDefault("z", null)).intValue();
					if(dimensions == 3)
					{
						if(x == null || y == null || z == null || x < 0 || y < 0 || z < 0 || x >= boardSize || y >= boardSize || z >= boardSize)
							throw new Exception("Inavlid coordinates in input");
						else
							cell = new Cell(s, x, y, z);
					}
					else
					{
						if(x == null || y == null || x < 0 || y < 0 || x >= boardSize || y >= boardSize)
							throw new Exception("Inavlid coordinates in input");
						else
							cell = new Cell(s, x, y);
					}
					if(cell != null)
						cells.add(cell);
				}
			}
			else if(randomize && regionSize != null && regionSize > 0 && regionSize < boardSize
					&& alivePercentage != null && alivePercentage > 0 && alivePercentage <= 100 && seed != null)
			{
				if(dimensions == 3)
					cells.addAll(Generator.generate3D(seed, boardSize, regionSize, alivePercentage));
				else
					cells.addAll(Generator.generate2D(seed, boardSize, regionSize, alivePercentage));
			}
			return new Input(dimensions, maxIterations, rule, boardSize, cells, test, autoPostProcess);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
    }
}
