import java.util.Random;

public class TruffleHelper {
	
	public static void printGameBoard(Cell[][] gameBoard) {

		for (Cell[] cell : gameBoard) {
			
			for (Cell insideCell : cell) {
				
				if (insideCell.isMine()) {
					System.out.print("*");
				} else if (!insideCell.isMine()) {
					System.out.print(insideCell.getNumAdjMines());
				}
			}
			
			System.out.println();
		}
	}
	
	public static Cell[][] gameBoardBuilder(int cols, int rows, int numTuffles) {
		
		Cell[][] gameBoard = new Cell[cols][rows];
		
		for (int i = 0; i < rows; i++) {
			
			for (int j = 0; j < cols; j++) {
				gameBoard[i][j] = new Cell(true, false, false, 0);
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
	
	public static int randInt(int max) {

	    int rand = new Random().nextInt(max);
	    return rand;
	    
	}
	
	

}
