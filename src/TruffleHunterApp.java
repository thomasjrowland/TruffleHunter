import java.util.ArrayList;
import java.util.Scanner;


public class TruffleHunterApp {

	public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
       
		Cell[][] gameBoard =  TruffleHelper.gameBoardBuilder(5, 5, 2);
		
		do{
		
		TruffleHelper.printGameBoard(gameBoard);
		
		
		System.out.print("Enter the input for row.");
		int inputRow = scnr.nextInt();
		
		System.out.print("Enter the input for col.");
		int inputCol = scnr.nextInt();
		
		TruffleHelper.uncoverOneSquare(gameBoard,inputRow, inputCol);
		} while(true);
	}

}
