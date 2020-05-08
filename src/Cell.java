
public class Cell {
	
	private boolean isCovered = true;
	private boolean isMine = false;
	private boolean hasFlag = false;
	private int numAdjMines = 0;
	private int numAdjZeros = 0;
	
	
	public Cell() {}
	
	public Cell(boolean isCovered, boolean isMine, boolean hasFlag, int numAdjMines, int numAdjZeros) {
		this.isCovered = isCovered;
		this.isMine = isMine;
		this.hasFlag = hasFlag;
		this.numAdjMines = numAdjMines;
		this.numAdjZeros = numAdjZeros;
	}

	public boolean isCovered() {
		return isCovered;
	}

	public void setCovered(boolean isCovered) {
		this.isCovered = isCovered;
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public boolean isHasFlag() {
		return hasFlag;
	}

	public void setHasFlag(boolean hasFlag) {
		this.hasFlag = hasFlag;
	}

	public int getNumAdjMines() {
		return numAdjMines;
	}

	public void setNumAdjMines(int numAdjMines) {
		this.numAdjMines = numAdjMines;
	}

	@Override
	public String toString() {
		return "Cell [isCovered=" + isCovered + ", isMine=" + isMine + ", hasFlag=" + hasFlag + ", numAdjMines="
				+ numAdjMines + "]";
	}

	public int getNumAdjZeros() {
		return numAdjZeros;
	}

	public void setNumAdjZeros(int numAdjZeros) {
		this.numAdjZeros = numAdjZeros;
	}
	
	
	
	
	

}
