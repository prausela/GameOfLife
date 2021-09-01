package front;
import java.util.Collection;
import back.Cell;
import back.Rule;

public class Input {
	private Integer dimensions;
	private Integer maxIterations;
	private Integer boardSize;
	private boolean test;
	private boolean autoPostProcess;
    private Collection<Cell> cells;
    private Rule rule;

	public Input(Integer dimensions, Integer maxIterations, Rule rule, Integer boardSize, Collection<Cell> cells, boolean test, boolean autoProcessTest)
	{
    	this.dimensions = dimensions;
    	this.maxIterations = maxIterations;
    	this.rule = rule;
    	this.boardSize = boardSize;
    	this.cells = cells;
    	this.test = test;
    	this.autoPostProcess = autoProcessTest;
	}

	public boolean isTest() {
		return test;
	}
	
	public boolean isAutoPostProcess() {
		return autoPostProcess;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule r) {
		this.rule = r;
	}

	public Integer getDimensions() {
		return dimensions;
	}

	public void setDimensions(Integer dimensions) {
		this.dimensions = dimensions;
	}

	public Integer getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(Integer maxIterations) {
		this.maxIterations = maxIterations;
	}

	public Integer getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(Integer boardSize) {
		this.boardSize = boardSize;
	}

	public Collection<Cell> getCells() {
		return cells;
	}

	public void setCells(Collection<Cell> cells) {
		this.cells = cells;
	}
}
