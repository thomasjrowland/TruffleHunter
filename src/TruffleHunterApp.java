import java.util.Scanner;


public class TruffleHunterApp {

	public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);
        
        System.out.println("   _______  ______   _     _  _______  _______  _        _______");
        System.out.println("  (_______)(_____ \\ (_)   (_)(_______)(_______)(_)      (_______)");
        System.out.println("      _     _____) ) _     _  _____    _____    _        _____");
        System.out.println("     | |   |  __  / | |   | ||  ___)  |  ___)  | |      |  ___)");
        System.out.println("     | |   | |  \\ \\ | |___| || |      | |      | |_____ | |_____");
        System.out.println("     |_|   |_|   |_| \\_____/ |_|      |_|      |_______)|_______)");
        System.out.println(" _     _  _     _  _______  _______  _______  ______     ______   ______");
        System.out.println("(_)   (_)(_)   (_)(_______)(_______)(_______)(_____ \\   (_____ \\ (______)");
        System.out.println(" _______  _     _  _     _     _     _____    _____) )    ____) ) _     _");
        System.out.println("|  ___  || |   | || |   | |   | |   |  ___)  |  __  /    / ____/ | |   | |");
        System.out.println("| |   | || |___| || |   | |   | |   | |_____ | |  \\ \\   | (_____ | |__/ /");
        System.out.println("|_|   |_| \\_____/ |_|   |_|   |_|   |_______)|_|   |_|  |_______)|_____/");
        System.out.println();
        System.out.println("Set flags to mark where you think Truffles might be hidden!  But be careful!");
        System.out.println("If you recklessly dig one up exploring, you'll damage it and lose the game!");
        System.out.println();                                                                              
       
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
		
		int flagOrExplore = Validator.getInt(scnr, "(1)Toggle Flag or (2)Explore Square (1 or 2) ", 1, 2);
		
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


