import java.util.Scanner;


public class TruffleHunterApp {

	public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);
       
		int startingRows = Validator.getInt(scnr, "How many ROWS/COLS: ", 5, 20);
		int startingCols = startingRows;
		int maxTruffles = (startingRows * startingCols) / 5;
		int startingNumTruffles = Validator.getInt(scnr, "How many TRUFFLES: ", 1, maxTruffles);
        
        Cell[][] gameBoard =  TruffleHelper.gameBoardBuilder(startingRows, startingCols, startingNumTruffles);
		
		int remainingTruffles = startingNumTruffles;
        
        do{
		
		TruffleHelper.printGameBoard(gameBoard, remainingTruffles, startingRows, startingCols);
		int inputRow = 0;
		int inputCol = 0;
		
		int flagOrExplore = Validator.getInt(scnr, "Toggle Flag or Explore Square (1 or 2) ", 1, 2);
		
			switch (flagOrExplore) {
				
				case 1:
					inputRow = Validator.getInt(scnr, "Enter the input for row. ", 1, startingRows);
					inputCol = Validator.getInt(scnr, "Enter the input for col. ", 1, startingCols);
					remainingTruffles = TruffleHelper.setFlag(gameBoard, inputRow-1, inputCol-1, remainingTruffles);
					break;
				
				case 2: 
					inputRow = Validator.getInt(scnr, "Enter the input for row. ", 1, startingRows);
					inputCol = Validator.getInt(scnr, "Enter the input for col. ", 1, startingCols);
					remainingTruffles = TruffleHelper.uncoverOneSquare(gameBoard,inputRow-1, inputCol-1, remainingTruffles, startingCols);
					break;
					
			}
			
		}  while(remainingTruffles > 0);
        
        System.out.println("GAME OVER!");
		
	}
	
}


