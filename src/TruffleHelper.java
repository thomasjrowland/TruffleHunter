import java.util.Random;

public class TruffleHelper {
	
	/* printGameBoard(Cell[][] gameBoard)
	 * 
	 * Prints out the current state of the gameboard for the
	 * console version of the app.
	 * 
	 */
	
	public static void printGameBoard(Cell[][] gameBoard) {
		
		for (Cell[] cell : gameBoard) {
			
			for (Cell insideCell : cell) {

				if (insideCell.isHasFlag()) {
					System.out.print("F");
				} else if (insideCell.isMine()) {
					System.out.print("X");
				} else if (insideCell.isCovered()) {
					System.out.print("X");
				} else if (!insideCell.isCovered()) {
					System.out.print(insideCell.getNumAdjMines());
				} 
			}

			System.out.println();
		}
	}
	
	/* gameBoardBuilder(int cols, int rows, int numTuffles)
	 * 
	 * Constructs the game board at the beginning of a new game.
	 * rows and cols are sent from the main method along with
	 * the number of truffles to be randomly hidden in the 
	 * field.  The completed board is returned to the main
	 * method as a 2D array of Cell objects.
	 * 
	 */
	
	public static Cell[][] gameBoardBuilder(int cols, int rows, int numTuffles) {
		
		// creates a 2D array list with input specified from the main method
		
		Cell[][] gameBoard = new Cell[rows][cols];
		
		for (int i = 0; i < rows; i++) {
			
			for (int j = 0; j < cols; j++) {
				gameBoard[i][j] = new Cell(true, false, false, 0);
			}
		}
		
		/*
		 * Takes the number of Truffles from the main method and
		 * sets the cell object's isMine value to true for random
		 * sets of coordinates within the specified bounds of the
		 * game board.  Loops until the specified number of truffles
		 * have been placed while avoiding placing one, if one
		 * has already been placed at the coordinate.
		 */
		int remainingTruffles = numTuffles;
		
		while (remainingTruffles != 0) {
			if (!gameBoard[randInt(rows)][randInt(cols)].isMine()) {
				gameBoard[randInt(rows)][randInt(cols)].setMine(true);
				remainingTruffles -= 1;
			}
		}
		
		/*
		 * Scans through each cell on the board and then looks through
		 * each adjacent cell determining if that cell is a truffle.
		 * If it is, a counter is incremented until all adjacent cells
		 * have been checked.  The total is then assigned to the 
		 * number of adjacent truffle count for each cell.  Try catch
		 * used to handle out of bounds exceptions for squares located
		 * around the perimeter.
		 */
		int adjTruffle = 0;
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
					adjTruffle = 0;

					try {
						if (gameBoard[x-1][y-1].isMine()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
					
					try {
						if (gameBoard[x-1][y].isMine()) {
							adjTruffle +=1;
						}						
					} catch (Exception e) {}
				
					try {					 
						if (gameBoard[x-1][y+1].isMine()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
					
					try {
						
						if (gameBoard[x][y-1].isMine()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
					
					try {
						if (gameBoard[x][y+1].isMine()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
					
					try {
						if (gameBoard[x+1][y-1].isMine()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
					
					try {
						if (gameBoard[x+1][y].isMine()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
					
					try {
						if (gameBoard[x+1][y+1].isMine()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
				
					gameBoard[x][y].setNumAdjMines(adjTruffle);
				}
			}
		return gameBoard;		
	}
	
	/*setFlag(Cell[][] gameBoard, int x, int y)
	 * 
	 * Changes the cell field of hasFlag to true or 
	 * false, or does nothing, depending on several
	 * conditions.
	 * 
	 * One of the two options the user has. 
	 */
		
	public static void setFlag(Cell[][] gameBoard, int x, int y) {
		
		if (gameBoard[x][y].isCovered()) {
			System.out.println("Already uncovered.  Cannot put flag!");
		} else if (!gameBoard[x][y].isHasFlag()) {
			gameBoard[x][y].setHasFlag(true);
		}else {
			gameBoard[x][y].setHasFlag(false);
		}
	}
	
	/* uncoverOneSquare(Cell [] [] gameBoard, int inputX, int inputY) {
	 * 
	 * The users other option apart from setting a flag.  Takes in coordinates
	 * from the user.  Checks to see if they have uncovered a truffle and if so
	 * ends the game.  If the space they have uncovered has 0 adjacent truffles
	 * a method is called to uncover all adjacent cells.  If the uncovered cell
	 * has any adjacent truffle, the cell will display the number of adjacent
	 * truffles in that cell the next time the board is printed. 
	 */
	
	public static void uncoverOneSquare(Cell [] [] gameBoard, int inputX, int inputY) {
		
		Cell currentCell = new Cell(); 
	
		currentCell = gameBoard[inputX][inputY];
		
		if (currentCell.isMine()) {
			gameOver();			
		} else if (currentCell.getNumAdjMines() == 0 && currentCell.isCovered()) {
			currentCell.setCovered(false);
			openAllAdjacentCells (gameBoard, inputX, inputY);
		} else if (currentCell.getNumAdjMines() > 0) {
			gameBoard[inputX][inputY].setCovered(false);
		}
	}
	
	/* openAllAdjacentCells (Cell [] [] gameBoard, int x, int y)
	 * 
	 * This method is called by the uncoverOneSquare method in the event
	 * the user uncovers a cell with no adjacent truffles.  This method
	 * uses the previous method to uncover each adjacent cell.  In the
	 * event the uncovered cell also has no adjacent truffles, that will
	 * call this method again for that cell as well.  Until all
	 * contiguous cells with no adjacent truffles have been run throuh
	 * this method.
	 * 
	 * Try catch used to handle out of bounds exceptions for cells on perimeter
	 * 
	 */

	public static void openAllAdjacentCells (Cell [] [] gameBoard, int x, int y) {

		try {
			uncoverOneSquare(gameBoard, x -1, y -1);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x-1, y);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x-1, y+1);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x, y-1);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x, y+1);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x+1, y-1);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x+1, y);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x+1, y+1);
		} catch(Exception e) { }
		
	}
	
	/* gameOver() 
	 * 
	 * Ends the game 
	 * 
	 */
	
	public static void gameOver() {
		
		System.out.println("GAME OVER!");
	}
	
	/* randInt(int max)
	 * 
	 * Random integer getter used to set truffles on the game board.
	 * 
	 */

	public static int randInt(int max) {

		int rand = new Random().nextInt(max);
		return rand;

	}

}
