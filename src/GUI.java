import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	//GUI app not set up for variable starting size, must leave at 9x16
	//could update later
	public final int startingRows = 9;
	public final int startingCols = 16;
	public final int startingNumTruffles = 20;
    
	//Creates a new 2d array of cell objects from our Console app logic
    public Cell[][] gameBoard =  TruffleHelper.gameBoardBuilder(startingRows, startingCols, startingNumTruffles);
	
	public int remainingTruffles = startingNumTruffles;
	
	//sets whether the game variables are being reset in the resetAll method
	public boolean resetter = false;
	
	//toggles whether a click will open a cell or place a flag
	public boolean flagger = false;
	
	Date startDate = new Date();
	Date endDate;
	
	int spacing = 1;
	
	String vicMes = "Nothing Yet!";
	
	//holds current mouse coordinates
	public int mx = -100;
	public int my = -100;
	
	//location setting variables for graphics elements
	public int smileyX = 605;
	public int smileyY = 5;
	
	public int smileyCenterX = smileyX + 35;
	public int smileyCenterY = smileyY + 35;
	
	public int flaggerX = 445;
	public int flaggerY = 5;
	
	public int flaggerCenterX = flaggerX + 35;
	public int flaggerCenterY = flaggerY + 35;
	
	public int timeX = 1130;
	public int timeY = 5;
	
	public int vicMessageX = 740;
	public int vicMessageY = 67;
	
	//timer initialized to zero
	public int sec = 0;
	
	//victory condition variables	
	public boolean happiness = true;
	
	public boolean victory = false;
	
	public boolean defeat = false;
	
	Random rand = new Random();
	
	public GUI() {
		this.setTitle("TRUFFLE HUNTER 2000");
		//pixel dimensions of window, don't fiddle with this if you haven't created a working variable size board
		this.setSize(1296, 836); 		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		//creates a board object and sets the window with it
		Board board = new Board();
		this.setContentPane(board);
		
		//adds handling for mouse movement
		Move move = new Move();
		this.addMouseMotionListener(move);
		
		//adds handling for mouse clicking
		Click click = new Click();
		this.addMouseListener(click);
		
	}
	
	//GameBoard class to create the visual board replaces print statement in console version
	public class Board extends JPanel {
		
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			
			//Icon Image loaders to import the tile icons
			
			ImageIcon iconCovered = null;
			String imgCoveredFile = "covered.png";
			URL imgURL = getClass().getClassLoader().getResource(imgCoveredFile);
			if (imgURL != null) {
			   iconCovered = new ImageIcon(imgURL);
			} else {
			   System.err.println("Couldn't find file: " + imgCoveredFile);
			}
		    final Image imgCovered = iconCovered.getImage();
		    
		    ImageIcon iconZero = null;
			String imgZeroFile = "0.png";
			URL imgURL1 = getClass().getClassLoader().getResource(imgZeroFile);
			if (imgURL1 != null) {
			   iconZero = new ImageIcon(imgURL1);
			} else {
			   System.err.println("Couldn't find file: " + imgCoveredFile);
			}
		    final Image imgZero = iconZero.getImage();
		    
		    ImageIcon iconTruffle = null;
			String imgTruffleFile = "truffle.png";
			URL imgURL2 = getClass().getClassLoader().getResource(imgTruffleFile);
			if (imgURL2 != null) {
			   iconTruffle = new ImageIcon(imgURL2);
			} else {
			   System.err.println("Couldn't find file: " + imgTruffleFile);
			}
		    final Image imgTruffle = iconTruffle.getImage();
		    
		    ImageIcon iconPig = null;
			String imgPigFile = "pig.png";
			URL imgURL3 = getClass().getClassLoader().getResource(imgPigFile);
			if (imgURL3 != null) {
			   iconPig = new ImageIcon(imgURL3);
			} else {
			   System.err.println("Couldn't find file: " + imgPigFile);
			}
		    final Image imgPig = iconPig.getImage();
		    
		    ImageIcon iconNotPig = null;
			String imgNotPigFile = "notapig.png";
			URL imgURL4 = getClass().getClassLoader().getResource(imgNotPigFile);
			if (imgURL4 != null) {
			   iconNotPig = new ImageIcon(imgURL4);
			} else {
			   System.err.println("Couldn't find file: " + imgPigFile);
			}
		    final Image imgNotPig = iconNotPig.getImage();
		    
		    
		    //sets background color
			g.setColor(new Color(0, 153, 0)); 
			g.fillRect(0, 0, 1280, 800);  
						
			//build a grid of squares on the gameboard 16x9
			for (int i = 0; i < 16; i++) {

				for (int j = 0; j < 9; j++) {

					g.drawImage(imgCovered, i * 80, j * 80 + 80, 80, 80, this);
				
					if (gameBoard[i][j].isTruffle() && defeat == true) {   		
					
						g.drawImage(imgTruffle, i * 80, j * 80 + 80, 80, 80, this);
						
					}
					if (!gameBoard[i][j].isCovered()) { 

						g.drawImage(imgZero, i * 80, j * 80 + 80, 80, 80, this);

						if  (gameBoard[i][j].isTruffle()) {
						
							g.setColor(Color.red);
						}
					}
					
					if (mx >= spacing+i*80 && mx < spacing +i*80+80-2*spacing && my >= spacing+j*80+80+26 && my < spacing+j*80+26+80+80-2*spacing) { //should change column color to red while mouse over
						
						g.setColor(Color.lightGray);
					}

					if (!gameBoard[i][j].isCovered()) {

						g.setColor(Color.black);
						
						//sets the color for drawing the numbers in the board
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
							
							//sets the font and draws the numbers on the board
							g.setFont(new Font("Tahoma", Font.BOLD, 40));
							g.drawString(Integer.toString(gameBoard[i][j].getNumAdjTruffles()), i*80+27, j*80+80+55);
						} else if (gameBoard[i][j].isTruffle()) {
							g.fillRect(i*80+10+20, j*80+80+20, 20, 40);
							g.fillRect(i*80+20, j*80+80+10+20, 40, 20);
							g.fillRect(i*80+5+20, j*80+80+5+20, 30, 30);
						}
					}
					
					
					//draws a flag(pig) in the box the mouse is within
					if (gameBoard[i][j].isHasFlag()) {
						
						gameBoard[i][j].setCovered(true);
						g.drawImage(imgPig, i * 80, j * 80 + 80, 80, 80, this);
					}
				}
			}
			
			
			//Creates the smiley face image that resets the game and frowns when you lose.
			
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
			
			//Sets the timer
			
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
			
			//sets the flagger button at the top
			
			if (flagger == true) {
				//g.setColor(Color.green);
				g.drawImage(imgPig, flaggerX, flaggerY, 70, 70, this);
			} else {
				//g.setColor(Color.red);
				g.drawImage(imgNotPig, flaggerX, flaggerY, 70, 70, this);
			}
		}
		
	}
	
	public class Move implements MouseMotionListener {
		
	
		@Override
		public void mouseDragged(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			
			//sets mouse pointer's current coordinates to public int variable
			mx = e.getX();
			my = e.getY(); 
		}
	}
	
	public class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			//rechecks mouse position of click
			mx = e.getX();
			my = e.getY();
			
			//opens the box that is clicked on based on what is underneath
					
			if (inBoxX() != -1 && inBoxY() != -1) {
				
				if (gameBoard[inBoxX()][inBoxY()].isTruffle() && flagger == false) {
					defeat = true;
					victory = false;
					happiness = false;
//				}
				
//				if (flagger == true && gameBoard[inBoxX()][inBoxY()].isCovered()) {
//					if (gameBoard[inBoxX()][inBoxY()].isHasFlag() == false) {
//						gameBoard[inBoxX()][inBoxY()].setHasFlag(true);
//					} else {
//						gameBoard[inBoxX()][inBoxY()].setHasFlag(false);
//					}
				} else if (flagger == false && gameBoard[inBoxX()][inBoxY()].isHasFlag() == false) {
					TruffleHelper.uncoverOneSquare(gameBoard, inBoxX(), inBoxY(), remainingTruffles, startingCols);	
				} else if (flagger == true) {
					TruffleHelper.setFlag(gameBoard, inBoxX(), inBoxY(), remainingTruffles);
				}
			}

			//determines if the mouse is inside the smiley using the method
			if (inSmiley() == true) {
				resetAll();
			}
			//determines if the mouse is inside the flag toggle using the method			
			if (inFlagger() == true) {
				if (flagger == true) {
					flagger = false;
				} else if (flagger == false) {
					flagger = true;
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	//determines if you win the game or lose, checked by main method
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
	
	
	//counts how many cells you have uncovered
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
	
	//resets all victory conditions and creates a new randomized gameboard
	public void resetAll() {
		
		resetter = true;//pauses main method checking victory status
		
		vicMes = "Nothing Yet!";
		
		flagger = false;
		
		startDate = new Date();
		
		vicMessageY = -50;
		
		happiness = true;
		victory = false;
		defeat = false;
		
		gameBoard =  TruffleHelper.gameBoardBuilder(startingRows, startingCols, startingNumTruffles);
		
		resetter = false;//allows main method to resume checking victory status
			
	}
	
	
	//defines the pixel coordinates for the inside of the reset smiley
	public boolean inSmiley() {
		
		int dif = (int) Math.sqrt((Math.pow((mx - smileyCenterX), 2) + Math.pow((my - smileyCenterY), 2)));
		
		if (dif < 35) {
			return true;
		} else {
			return false;
		}
	}
	
	//defines the pixel coordinates for the inside of the flagger toggle	
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
}
//whoo-hoo made it to 500 lines of code!	

