import java.util.Scanner;


public class TruffleHunterApp {

	public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);
       
		Cell[][] gameBoard =  TruffleHelper.gameBoardBuilder(10, 10, 35);
		
		do{
		
		TruffleHelper.printGameBoard(gameBoard);
		int inputRow = 0;
		int inputCol = 0;
		
		System.out.print("Toggle Flag or Explore Square (1 or 2) ");
		int flagOrExplore = scnr.nextInt();
		
			switch (flagOrExplore) {
				
				case 1:
					System.out.print("Enter the input for row.");
					inputRow = scnr.nextInt();
					System.out.print("Enter the input for col.");
					inputCol = scnr.nextInt();
					TruffleHelper.setFlag(gameBoard, inputRow-1, inputCol-1);
					break;
				
				case 2: 
					System.out.print("Enter the input for row.");
					inputRow = scnr.nextInt();
					System.out.print("Enter the input for col.");
					inputCol = scnr.nextInt();
					TruffleHelper.uncoverOneSquare(gameBoard,inputRow-1, inputCol-1);
					break;
					
			}
			
		}  while(true);
		
	}
	
}


