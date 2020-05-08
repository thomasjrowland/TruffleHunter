import java.util.Random;

public class TruffleHelper {
	
	public static void printGameBoard(Cell[][] gameBoard) {
		
		//checkForZeros(gameBoard);
		
		
		for (Cell[] cell : gameBoard) {
			
			for (Cell insideCell : cell) {

				if (insideCell.isMine()) {
					System.out.print("*");
				} else if (insideCell.isHasFlag()) {
					System.out.print("F");
				} else if (insideCell.isCovered()) {
					System.out.print("X");
				} else if (!insideCell.isCovered()) {
					System.out.print(insideCell.getNumAdjMines());
				} 
				
//				else if (!insideCell.isMine()) {
//					System.out.print(insideCell.getNumAdjMines());
//				
//				}
				
				}

			System.out.println();
		}
	}
	
	public static Cell[][] gameBoardBuilder(int cols, int rows, int numTuffles) {
		
		Cell[][] gameBoard = new Cell[rows][cols];
		
		for (int i = 0; i < rows; i++) {
			
			for (int j = 0; j < cols; j++) {
				gameBoard[i][j] = new Cell(true, false, false, 0, 0);
			}
		}
		
		int remainingTruffles = numTuffles;
		
		while (remainingTruffles != 0) {
			if (!gameBoard[randInt(rows)][randInt(cols)].isMine()) {
				gameBoard[randInt(rows)][randInt(cols)].setMine(true);
				remainingTruffles -= 1;
			}
		}
		
		//set the adj truff values
		//set in a trycatch
		int adjTruffle = 0;
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
					adjTruffle = 0;
				if (x == 0 && y == 0) {
					if (gameBoard[x][y+1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y+1].isMine()) {
						adjTruffle +=1;
					}
				} else if (x == 0 && y == cols-1) {
					if (gameBoard[x][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y].isMine()) {
						adjTruffle +=1;
					} 
				} else if (x == rows -1 && y ==0) {
					if (gameBoard[x-1][y].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x-1][y+1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x][y+1].isMine()) {
						adjTruffle +=1;
					} 
				} else if (x == rows -1 && y == cols -1) {
					if (gameBoard[x-1][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x-1][y].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x][y-1].isMine()) {
						adjTruffle +=1;
					} 
				} else if (x == 0) {
					if (gameBoard[x][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x][y+1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y+1].isMine()) {
						adjTruffle +=1;
					}
				} else if (x == rows - 1) {
					if (gameBoard[x-1][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x-1][y].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x-1][y+1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x][y+1].isMine()) {
						adjTruffle +=1;
					} 
				} else if (y == 0) {
					if (gameBoard[x-1][y].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x-1][y+1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x][y+1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y+1].isMine()) {
						adjTruffle +=1;
					}
				} else if (y == cols - 1) {
					if (gameBoard[x-1][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x-1][y].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y].isMine()) {
						adjTruffle +=1;
					} 
				} else {
					if (gameBoard[x-1][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x-1][y].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x-1][y+1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x][y+1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y-1].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y].isMine()) {
						adjTruffle +=1;
					} 
					if (gameBoard[x+1][y+1].isMine()) {
						adjTruffle +=1;
					}
				}

				gameBoard[x][y].setNumAdjMines(adjTruffle);
				
			}
		}
		return gameBoard;		
		}
		
		
		
	public static void setFlag(Cell[][] gameBoard, int x, int y) {
		
		if (!gameBoard[x][y].isHasFlag()) {
			gameBoard[x][y].setHasFlag(true);
		}else {
			gameBoard[x][y].setHasFlag(false);
		}
	}
	
	
	
	public static void uncoverOneSquare(Cell [] [] gameBoard, int inputX, int inputY) {
		
		Cell currentCell = new Cell(); 
	
		//inputX -= 1;
		//inputY -= 1;
		
		currentCell = gameBoard[inputX][inputY];
		
		if (currentCell.getNumAdjMines() == 0 && currentCell.isCovered()) {
			currentCell.setCovered(false);
			openAllAdjacentCells (gameBoard, inputX, inputY);
		}
		else if (currentCell.getNumAdjMines() > 0) {
			gameBoard[inputX][inputY].setCovered(false);
		}
	}

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
	
	
	
//	//method checkForZeros	
//	public static void checkForZeros (Cell[][] gameBoard) {
//		
//		int numZeros = 0;
//		
//		
//		
//			for (int i = 0 ; i < gameBoard.length ; i++) {
//				Cell[] col = gameBoard[i];
//			
//				for (int k = 0 ; k < col.length ; k++) {
//					Cell c = gameBoard[i][k];
//				
//					if (!c.isCovered() && c.getNumAdjMines() == 0) {
//						openAllAdjacentCells(gameBoard, i, k);
//						numZeros = countAdjZeros(gameBoard, i, k);
//					}
//				}
//			}
//			if (numZeros > 0) {
//				//checkForZeros(gameBoard);
//			}
//			
//			System.out.println(numZeros);
//		
//		
//		
//	}
//	
//	//method count adj zeros
//	public static int countAdjZeros(Cell[][] gameBoard, int x, int y) {
//		
//		int numZeros = 0;
//		
//		try {
//			if (gameBoard[x-1][y-1].getNumAdjMines()==0) {
//				numZeros +=1;
//			} 
//		} catch(Exception e) { }
//		
//		try {
//			if (gameBoard[x-1][y].getNumAdjMines()==0) {
//				numZeros +=1;
//			}
//		} catch(Exception e) { }
//		
//		try {
//			if (gameBoard[x-1][y+1].getNumAdjMines()==0) {
//				numZeros +=1;
//			}
//		} catch(Exception e) { }
//		
//		try {
//			if (gameBoard[x][y-1].getNumAdjMines()==0) {
//				numZeros +=1;
//			}
//		} catch(Exception e) { }
//		
//		try {
//			if (gameBoard[x][y+1].getNumAdjMines()==0) {
//				numZeros +=1;
//			}
//		} catch(Exception e) { }
//		
//		try {
//			if (gameBoard[x+1][y-1].getNumAdjMines()==0) {
//				numZeros +=1;
//			}
//		} catch(Exception e) { }
//		
//		try {
//			if (gameBoard[x+1][y].getNumAdjMines()==0) {
//				numZeros +=1;
//			}
//		} catch(Exception e) { }
//		
//		try {
//			if (gameBoard[x+1][y+1].getNumAdjMines()==0) {
//				numZeros +=1;
//			}
//		} catch(Exception e) { }
//		
//		return numZeros;
//		
//	}
//	
//	//method setFlag
//	//method gameOver


	public static int randInt(int max) {

		int rand = new Random().nextInt(max);
		return rand;

	}

	

}
