import javax.swing.*;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JFrame {
	
	public final int startingRows = 9;
	public final int startingCols = 16;
	public final int startingNumTruffles = 10;
    
    public Cell[][] gameBoard =  TruffleHelper.gameBoardBuilder(startingRows, startingCols, startingNumTruffles);
	
	public int remainingTruffles = startingNumTruffles;
	
	public boolean resetter = false;
	
	//sets click to flagger
	public boolean flagger = false;
	
	Date startDate = new Date();
	Date endDate;
	
	int spacing = 1;
	
//	int neighs = 0;
	
	String vicMes = "Nothing Yet!";
	
	public int mx = -100; //holds mouse coordinates
	public int my = -100;
	
	public int smileyX = 605;
	public int smileyY = 5;
	
	public int smileyCenterX = smileyX + 35;
	public int smileyCenterY = smileyY + 35;
	
	public int flaggerX = 445;
	public int flaggerY = 5;
	
	public int flaggerCenterX = flaggerX + 35;
	public int flaggerCenterY = flaggerY + 35;
	
	public int sec = 0;
		
	public int timeX = 1130;
	public int timeY = 5;
	
	public int vicMessageX = 740;
	public int vicMessageY = 67;
	
	public boolean happiness = true;
	
	public boolean victory = false;
	
	public boolean defeat = false;
	
	Random rand = new Random();
	
//	int[][] mines = new int[16][9];
//	int[][] neighbors = new int[16][9];
//	boolean[][] revealed = new boolean[16][9];
//	boolean[][] flagged = new boolean[16][9];
	
	public GUI() {
		this.setTitle("TRUFFLE HUNTER 2000"); 	//game title in window
		this.setSize(1296, 836); 		//pixel dimensions of window (Height adds 29px to account for the title bar)(width add 3 per side)
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
//		//setting random mines in 20%ish spaces
//		for (int i = 0; i < 16; i++) {
//			for (int j = 0; j < 9; j++) {
//				if (rand.nextInt(100) < 20) {
//					mines[i][j] = 1;
//				} else {
//					mines[i][j] = 0;
//				}
//				revealed[i][j] = false;
//			}
//		}
//		
//		//get adjacent mines count
//		for (int i = 0; i < 16; i++) {
//			for (int j = 0; j < 9; j++) {
//				neighs = 0;
//				for (int m = 0; m < 16; m++) {
//					for (int n = 0; n < 9; n++) {
//						if (!(m == i && n == j)) {
//							if (isN(i,j,m,n) == true) {
//								neighs++;
//							}
//						}
//					}
//					neighbors[i][j] = neighs;
//				}
//			}
//		}
		
		Board board = new Board();
		this.setContentPane(board); //sends the board object to the GUI window
		
		Move move = new Move();
		this.addMouseMotionListener(move);
		
		Click click = new Click();
		this.addMouseListener(click);
		
	}
	
	//GameBoard class to create the visual board replaces print statement in console version
	public class Board extends JPanel {
		
		public void paintComponent(Graphics g) {
			
			g.setColor(Color.DARK_GRAY); 
			g.fillRect(0, 0, 1280, 800); //creates a dark grey rectangle that fills the entire window 
						
			//build a grid of squares on the gameboard 16x9
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 9; j++) {
					g.setColor(Color.gray);
					if (gameBoard[i][j].isTruffle() && defeat == true) {   		//will display all mine locations
						g.setColor(Color.orange);
					}
					if (!gameBoard[i][j].isCovered()) { //(revealed[i][j] == true) {
						g.setColor(Color.white);
						if  (gameBoard[i][j].isTruffle()) {
							g.setColor(Color.red);
						}
					}
					
					if (mx >= spacing+i*80 && mx < spacing +i*80+80-2*spacing && my >= spacing+j*80+80+26 && my < spacing+j*80+26+80+80-2*spacing) { //should change column color to red while mouse over
						g.setColor(Color.lightGray);
					}
					g.fillRect(spacing+i*80, spacing+j*80+80, 80-2*spacing, 80-2*spacing);
					if (!gameBoard[i][j].isCovered()) {
						g.setColor(Color.black);
						if  (!gameBoard[i][j].isTruffle() && gameBoard[i][j].getNumAdjTruffles() != 0) {
							if (gameBoard[i][j].getNumAdjTruffles() == 1) {
								g.setColor(Color.blue);
							} else if (gameBoard[i][j].getNumAdjTruffles() == 2) {
								g.setColor(Color.green);
							} else if (gameBoard[i][j].getNumAdjTruffles() == 3) {
								g.setColor(Color.red);
							} else if (gameBoard[i][j].getNumAdjTruffles() == 4) {
								g.setColor(Color.cyan);
							} else if (gameBoard[i][j].getNumAdjTruffles() == 5) {
								g.setColor(Color.magenta);
							} else if (gameBoard[i][j].getNumAdjTruffles() == 6) {
								g.setColor(Color.orange);
							} else if (gameBoard[i][j].getNumAdjTruffles() == 7) {
								g.setColor(Color.black);
							} else if (gameBoard[i][j].getNumAdjTruffles() == 8) {
								g.setColor(Color.darkGray);
							}
							g.setFont(new Font("Tahoma", Font.BOLD, 40));
							g.drawString(Integer.toString(gameBoard[i][j].getNumAdjTruffles()), i*80+27, j*80+80+55);
						} else if (gameBoard[i][j].isTruffle()) {
							g.fillRect(i*80+10+20, j*80+80+20, 20, 40);
							g.fillRect(i*80+20, j*80+80+10+20, 40, 20);
							g.fillRect(i*80+5+20, j*80+80+5+20, 30, 30);
							
							
						}
					}
					
					
					//flag setter
					if (gameBoard[i][j].isHasFlag()) {
						
						g.setColor(Color.black);
						g.fillRect(i*80 + 32, j*80+80 + 15, 5, 40);
						g.fillRect(i*80 + 20, j*80+80 + 50, 30, 10);
						g.setColor(Color.red);
						g.fillRect(i*80 + 16, j*80+80 + 15, 20, 15);
						
					}
					
				}
			}
			
			//smiley painting
			
			g.setColor(Color.yellow);
			g.fillOval(smileyX, smileyY, 70, 70);
			g.setColor(Color.black);
			g.fillOval(smileyX + 15, smileyY + 20, 10, 10);
			g.fillOval(smileyX + 45, smileyY + 20, 10, 10);
			if (happiness == true) {
				g.fillRect(smileyX + 20, smileyY + 50, 30, 5);
				g.fillRect(smileyX + 17, smileyY + 45, 5, 5);
				g.fillRect(smileyX + 48, smileyY + 45, 5, 5);
			} else {
				g.fillRect(smileyX + 20, smileyY + 45, 30, 5);
				g.fillRect(smileyX + 17, smileyY + 50, 5, 5);
				g.fillRect(smileyX + 48, smileyY + 50, 5, 5);				
			}
			
			//time counter
			
			g.setColor(Color.black);
			g.fillRect(timeX, timeY, 140, 70);
			if (defeat == false && victory == false) {
				sec = (int) ((new Date().getTime() - startDate.getTime()) / 1000);
			}			
			if (sec >  999) {
				sec = 999;
			}
			g.setColor(Color.white);
			if (victory == true) {
				g.setColor(Color.green);
			} else if (defeat == true) {
				g.setColor(Color.red);
			} 
			g.setFont(new Font("Tahoma", Font.PLAIN, 80));
			if (sec < 10) {
				g.drawString("00" + Integer.toString(sec), timeX, timeY+65);
			} else if (sec < 100) {
				g.drawString("0" + Integer.toString(sec), timeX, timeY+65);
			} else {
				g.drawString(Integer.toString(sec), timeX, timeY+65);
			}
			
			//victory message
			
			if (victory == true) {
				g.setColor(Color.green);
				vicMes = "YOU WIN!";
			} else if (defeat == true) {
				g.setColor(Color.red);
				vicMes = "YOU LOSE!";
			}
			
			if (victory == true || defeat == true) {
				g.setFont(new Font("Tahoma", Font.PLAIN, 70));
				g.drawString(vicMes, vicMessageX, vicMessageY);				
			}
			
			//flagger button
			g.setColor(Color.black);
			g.fillRect(flaggerX + 32, flaggerY + 15, 5, 40);
			g.fillRect(flaggerX + 20, flaggerY + 50, 30, 10);
			g.setColor(Color.red);
			g.fillRect(flaggerX + 16, flaggerY + 15, 20, 15);
			
			if (flagger == true) {
				g.setColor(Color.green);
			} else {
				g.setColor(Color.red);
			}
			
			g.drawOval(flaggerX, flaggerY, 70, 70);
			g.drawOval(flaggerX+1, flaggerY+1, 68, 68);
			g.drawOval(flaggerX+2, flaggerY+2, 66, 66);
			
		}
		
	}
	
	public class Move implements MouseMotionListener {
		
	
		@Override
		public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			
			//System.out.println("Mouse was moved"); // method called when mouse moves
			mx = e.getX();
			my = e.getY(); //returns mouse pointer coordinates to public int variable
			//System.out.println("X" + mx + ", Y" + my);
		
		}
	}
	
	public class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			//resets the position of the mouse
			mx = e.getX();
			my = e.getY();
			
			//opens the box that is clicked on
					
			if (inBoxX() != -1 && inBoxY() != -1) {
				
				if (gameBoard[inBoxX()][inBoxY()].isTruffle() && flagger == false) {
					defeat = true;
					victory = false;
					happiness = false;
				}
				
				if (flagger == true && gameBoard[inBoxX()][inBoxY()].isCovered()) {
					if (gameBoard[inBoxX()][inBoxY()].isHasFlag() == false) {
						gameBoard[inBoxX()][inBoxY()].setHasFlag(true);
					} else {
						gameBoard[inBoxX()][inBoxY()].setHasFlag(false);
					}
				} else if (flagger == false && gameBoard[inBoxX()][inBoxY()].isHasFlag() == false) {
					TruffleHelper.uncoverOneSquare(gameBoard, inBoxX(), inBoxY(), remainingTruffles, startingCols);	
					//revealed[inBoxX()][inBoxY()] = true;
				}
				
			}
			
			if (inSmiley() == true) {
				resetAll();
			}
			
			if (inFlagger() == true) {
				if (flagger == true) {
					flagger = false;
				} else if (flagger == false) {
					flagger = true;
				}
			}
			
			
			
			
			//print method for testing
//			if (inBoxX() != -1 && inBoxY() != -1) {
//				System.out.println("Mouse is in box " + inBoxX() + ", " + inBoxY());  //method called when mouse is clicked
//				System.out.println("Number of adj mines is: " + + neighbors[inBoxX()][inBoxY()]);
//			} else {
//				System.out.println("not in a box");
//			}
			
//			if (inSmiley() == true) {
//				System.out.println("in the smiley");
//			} else {
//				System.out.println("Not in the smiley");
//			}
//			if (mines[inBoxX()][inBoxY()] == 1) {
//			System.out.println("THIS IS A MINE");
//			}			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void checkVictoryStatus() {
		
		if (defeat == false) {
		
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 9; j++) {
					if (gameBoard[i][j].isCovered() == false && gameBoard[i][j].isTruffle()) {
						defeat = true;
						happiness = false;
						endDate = new Date();
					}
				}
			}
		}
		
		
		
		if (totalBoxesRevealed() >= 144 - startingNumTruffles && victory == false) {
			victory = true;
			endDate = new Date();
		}
	}
	
//	public int totalMines() {
//		
//		int total = 0;
//		for (int i = 0; i < 16; i++) {
//			for (int j = 0; j < 9; j++) {
//				if (gameBoard[i][j].isTruffle()) {
//					total++;					
//				}
//			}
//		}
//		return total;
//	}
	
	public int totalBoxesRevealed() {
		
		int total = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (!gameBoard[i][j].isCovered()) {
					total++;					
				}
			}
		}
		return total;
	}
	
	public void resetAll() {
		
		resetter = true;
		
		vicMes = "Nothing Yet!";
		
		flagger = false;
		
		startDate = new Date();
		
		vicMessageY = -50;
		
		happiness = true;
		victory = false;
		defeat = false;
		
		gameBoard =  TruffleHelper.gameBoardBuilder(startingRows, startingCols, startingNumTruffles);
		
		resetter = false;
			
	}
	
	public boolean inSmiley() {
		
		int dif = (int) Math.sqrt((Math.pow((mx - smileyCenterX), 2) + Math.pow((my - smileyCenterY), 2)));
		
		if (dif < 35) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean inFlagger() {
		
		int dif = (int) Math.sqrt((Math.pow((mx - flaggerCenterX), 2) + Math.pow((my - flaggerCenterY), 2)));
		
		if (dif < 35) {
			return true;
		} else {
			return false;
		}
	}
	
	//two methods below will reveal the coordinate locations of a clicked box
	// if not in a box, method will return a -1
	
	public int inBoxX() {
		
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (mx >= spacing+i*80 && mx < spacing +i*80+80-2*spacing && my >= spacing+j*80+80+26 && my < spacing+j*80+26+80+80-2*spacing) { 
					return i;
				}
			}
		}
		
		return -1;
	}
	
	public int inBoxY() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (mx >= spacing+i*80 && mx < spacing +i*80+80-2*spacing && my >= spacing+j*80+80+26 && my < spacing+j*80+26+80+80-2*spacing) { 
					return j;
				}
			}
		}
		
		return -1;
	}
	
//	//checks if box is adjacent
//	public boolean isN(int mX, int mY, int cX, int cY) {
//		
//		if (mX - cX < 2 && mX - cX > -2 && mY - cY < 2 && mX - cY > -2 && mines[cX][cY] == 1) {
//			return true;
//		} else {
//			return false;
//		}
//	}
	
}
	

