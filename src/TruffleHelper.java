import java.util.Random;

public class TruffleHelper {
	
	/* printGameBoard(Cell[][] gameBoard)
	 * 
	 * Prints out the current state of the gameboard for the
	 * console version of the app.
	 * 
	 */
	
	public static void printGameBoard(Cell[][] gameBoard, int remainingTruffles, int rows, int cols) {
		
		int displayRowNum = 1;
		
		System.out.println("REMAINING TRUFFLES: " + remainingTruffles);
		System.out.print("    ");
		
		for (int i = 1; i <= cols; i++) {
			System.out.printf("%3d", i);
		}
		
		System.out.println();
		
		System.out.print("     ");
		
		for (int i = 1; i <= cols; i++) {
			System.out.print("___");
		}
		
		System.out.println();
		
		for (Cell[] cell : gameBoard) {
			
			System.out.printf("%3d |", displayRowNum);
			displayRowNum++;
			
			for (Cell insideCell : cell) {

				if (insideCell.isHasFlag()) {
					System.out.print("[P]");
				//} else if (insideCell.isMine()) {
				//	System.out.print(" Ö� ");
				} else if (insideCell.isCovered()) {
					System.out.print("[_]");
				} else if (!insideCell.isCovered() && insideCell.getNumAdjTruffles() == 0) {
					System.out.print(":::");
				} else if (!insideCell.isCovered()) {
					System.out.print(":" + insideCell.getNumAdjTruffles() + ":");
				} 
			}

			System.out.println();
		}
		
		System.out.println();
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
		
		while (remainingTruffles > 0) {
			
			int x = 0;
			int y = 0;
			
			x = randInt(rows);
			y = randInt(cols);
			
			if (!gameBoard[x][y].isTruffle()) {
				gameBoard[x][y].setTruffle(true);
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
						if (gameBoard[x-1][y-1].isTruffle()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
					
					try {
						if (gameBoard[x-1][y].isTruffle()) {
							adjTruffle +=1;
						}						
					} catch (Exception e) {}
				
					try {					 
						if (gameBoard[x-1][y+1].isTruffle()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
					
					try {
						
						if (gameBoard[x][y-1].isTruffle()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
					
					try {
						if (gameBoard[x][y+1].isTruffle()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
					
					try {
						if (gameBoard[x+1][y-1].isTruffle()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
					
					try {
						if (gameBoard[x+1][y].isTruffle()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
					
					try {
						if (gameBoard[x+1][y+1].isTruffle()) {
							adjTruffle +=1;
						}
					} catch (Exception e) {}
				
					gameBoard[x][y].setNumAdjTruffles(adjTruffle);
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
		
	public static int setFlag(Cell[][] gameBoard, int x, int y, int remainingTruffles) {
		
		int truffleCounter = remainingTruffles;
		
		if (!gameBoard[x][y].isHasFlag() && gameBoard[x][y].isTruffle()) {
			gameBoard[x][y].setHasFlag(true);
			truffleCounter -= 1;
		}else if (!gameBoard[x][y].isHasFlag() && !gameBoard[x][y].isTruffle()) {
			gameBoard[x][y].setHasFlag(true);
		} else if (gameBoard[x][y].isHasFlag() && gameBoard[x][y].isTruffle()) {
			gameBoard[x][y].setHasFlag(false);
			truffleCounter += 1;			
		} else if (gameBoard[x][y].isHasFlag() && !gameBoard[x][y].isTruffle()) {
			gameBoard[x][y].setHasFlag(false);
		}
		
		return truffleCounter;
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
	
	public static int uncoverOneSquare(Cell [][] gameBoard, int x, int y, int remainingTruffles, int cols) {
		
		int truffleCounter = remainingTruffles;
		Cell currentCell = new Cell(); 
	
		currentCell = gameBoard[x][y];
		
		if (currentCell.isTruffle()) {
			truffleCounter = gameOver(gameBoard, truffleCounter, cols);			
		} else if (currentCell.getNumAdjTruffles() == 0 && currentCell.isCovered()) {
			currentCell.setCovered(false);
			openAllAdjacentCells (gameBoard, x, y, cols);
		} else if (currentCell.getNumAdjTruffles() > 0) {
			gameBoard[x][y].setCovered(false);
		}
		
		return truffleCounter;
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

	public static void openAllAdjacentCells (Cell [][] gameBoard, int x, int y, int cols) {

		try {
			uncoverOneSquare(gameBoard, x -1, y -1, 1, 1);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x-1, y, 1, 1);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x-1, y+1, 1, 1);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x, y-1, 1, 1);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x, y+1, 1, 1);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x+1, y-1, 1, 1);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x+1, y, 1, 1);
		} catch(Exception e) { }
		
		try {
			uncoverOneSquare(gameBoard,x+1, y+1, 1, 1);
		} catch(Exception e) { }
		
	}
	
	/* gameOver() 
	 * 
	 * Ends the game 
	 * 
	 */
	
	public static int gameOver(Cell [][] gameBoard, int remainingTruffles, int cols) {
		
		int displayRowNum = 1;
		
		System.out.println("REMAINING TRUFFLES: " + remainingTruffles);
		System.out.print("    ");
		
		for (int i = 1; i <= cols; i++) {
			System.out.printf("%3d", i);
		}
		
		System.out.println();
		
		System.out.print("     ");
		
		for (int i = 1; i <= cols; i++) {
			System.out.print("___");
		}
		
		System.out.println();
		
		for (Cell[] cell : gameBoard) {
			
			System.out.printf("%3d |", displayRowNum);
			displayRowNum++;
			
			for (Cell insideCell : cell) {

				if (insideCell.isHasFlag() && !insideCell.isTruffle()) {
					System.out.print("[X]");
				} else if (insideCell.isTruffle()) {
					System.out.print(" * ");
				} else if (insideCell.isCovered()) {
					System.out.print("[_]");
				} else if (!insideCell.isCovered() && insideCell.getNumAdjTruffles() == 0) {
					System.out.print(":::");
				} else if (!insideCell.isCovered()) {
					System.out.print(":" + insideCell.getNumAdjTruffles() + ":");
				} 
			}

			System.out.println();
		}
		
		System.out.println("YOU LOSE!");
		return 0;
		
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
