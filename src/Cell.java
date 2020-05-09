
/*
 * Cell object class.
 *  
 * Cells are stored in a 2D array to create a dynamic
 * randomly generated game board that changes each
 * play-through.
 * 
 */

public class Cell {
	
	private boolean isCovered = true;
	private boolean isTruffle = false;
	private boolean hasFlag = false;
	private int numAdjTruffles = 0;
	
	public Cell() {}
	
	public Cell(boolean isCovered, boolean isTruffle, boolean hasFlag, int numAdjMines) {
		this.isCovered = isCovered;
		this.isTruffle = isTruffle;
		this.hasFlag = hasFlag;
		this.numAdjTruffles = numAdjMines;
	}

	//GETTERS AND SETTERS:
	
	public boolean isCovered() {
		return isCovered;
	}

	public void setCovered(boolean isCovered) {
		this.isCovered = isCovered;
	}

	public boolean isTruffle() {
		return isTruffle;
	}

	public void setTruffle(boolean isTruffle) {
		this.isTruffle = isTruffle;
	}

	public boolean isHasFlag() {
		return hasFlag;
	}

	public void setHasFlag(boolean hasFlag) {
		this.hasFlag = hasFlag;
	}

	public int getNumAdjTruffles() {
		return numAdjTruffles;
	}

	public void setNumAdjTruffles(int numAdjTruffles) {
		this.numAdjTruffles = numAdjTruffles;
	}

	@Override
	public String toString() {
		return "Cell [isCovered=" + isCovered + ", isTruffle=" + isTruffle + ", hasFlag=" + hasFlag + ", numAdjTruffles="
				+ numAdjTruffles + "]";
	}
}
