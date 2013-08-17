import java.util.*;

/**
 * Tray class that carries all blocks and relevant information.
 * @author Dickson Lui, Jesse Luo
 */

public class Tray implements Comparable<Tray> {
	
	private ArrayList<Block> myBlocks;
	private boolean[][] mySpace;
	private int myHeight;
	private int myWidth;
	public int myScore;
	
	public Tray (int rows, int columns) {
		// Tray constructor that creates a Tray given the number of rows and columns.
		myHeight = rows;
		myWidth = columns;
		myBlocks = new ArrayList<Block>(rows * columns / 2);
		mySpace = new boolean[rows][columns];
	}
	
	private Tray (Tray previousTray, int move) {
		// Constructs a board given an existing board and a move.
		myHeight = previousTray.getHeight();
		myWidth = previousTray.getWidth();
		
	}
	
	public ArrayList<Block> getBlocks () {
		// Returns the ArrayList containing all the blocks on the tray.
		return this.myBlocks;
	}
	
	public boolean[][] getSpace () {
		// Returns the two-dimensional boolean representation of the board.
		// All spaces filled in are marked as true.
		return this.mySpace;
	}
	
	public int getHeight () {
		// Returns the height of the tray.
		return this.myHeight;
	}
	
	public int getWidth () {
		// Returns the width of the tray.
		return this.myWidth;
	}
	
	public void addBlock (String block) {
		// Block adding class used in TrayReader to populate the board.
		Block toAdd = new Block(block);
		for (int a = toAdd.getRow(); a < toAdd.getRow() + toAdd.getHeight(); a++) {
			for (int b = toAdd.getCol(); b < toAdd.getCol() + toAdd.getWidth(); b++) {
				this.mySpace[a][b] = true;
			}
		}
		myBlocks.add(toAdd);
	}
	
	public void sortBlocks () {
		// When called on a Tray class, InsertionSorts the block into increasing order 
		// by row, then column.
		ArrayList<Block> toBeSorted = this.getBlocks();
		ArrayList<Block> tempBlocks = new ArrayList<Block>(toBeSorted.size());
		
		while (toBeSorted.size() > 0) {
			Block currBlock = toBeSorted.get(0);
			for (Block compareBlock: toBeSorted) {
				if (compareBlock.getRow() < currBlock.getRow()) {
					currBlock = compareBlock;
				} else if (compareBlock.getRow() == currBlock.getRow()) {
					if (compareBlock.getCol() < currBlock.getCol()) {
						currBlock = compareBlock;
					}
				}
			}
			tempBlocks.add(currBlock);
			toBeSorted.remove(currBlock);
		}
		this.myBlocks = tempBlocks;
	}
	
	private boolean hasBlock (int row, int column) {
		// Returns true if a block occupies this space and false if it does not.
		return this.getSpace()[row][column];
	}
	
	public LinkedList<Tray> generateMoves () {
		// When called on a tray, generates a Linked List structure with every possible tray
		// configuration that could result from moving a block.
		LinkedList<Tray> possibleTrays = new LinkedList<Tray>();
		
		for (Block currBlock: this.getBlocks()) {
			
			boolean canUp = true;
			boolean canDown = true;
			boolean canLeft = true;
			boolean canRight = true;
			
			// If this block can move up, generate the move and add it to the LinkedList.
			if (currBlock.getRow() != 0) {
				
				if (canUp) {
					// Generate move here.
				}
			}
			// If this block can move down, generate the move and add it to the LinkedList.
			if (currBlock.getRow() + currBlock.getHeight() < this.getHeight()) {
				
				if (canDown) {
					// Generate move here.
				}
			}
			// If this block can move left, generate the move and add it to the LinkedList.
			if (currBlock.getCol() != 0) {
				
				if (canLeft) {
					// Generate move here.
				}
			}
			// If this block can move right, generate the move and add it to the LinkedList.
			if (currBlock.getCol() + currBlock.getWidth() < this.getWidth()) {
				for (int i = currBlock.getRow(); i < currBlock.getRow() + currBlock.getHeight(); i++) {
					if (this.getSpace()[i][currBlock.getCol() + currBlock.getWidth()]) {
						canRight = false;
					}
				}
				if (canRight) {
					// Generate move here.
				}
			}
			
		}
		
		return possibleTrays;
	}
	
	public int score (ArrayList<Block> current, ArrayList<Block> goal) {
		// Scores the Tray based on how close the current board configuration is to the goal board
		// configuration. This will help us implement our comparable interface to determine the best
		// Tray route to take.
		int [] scores = new int[goal.size()];
		ArrayList<Integer> blockUsed = new ArrayList<Integer>();
		
		for (int i = 0; i < goal.size(); i++) {
			scores[i] = 99999999;
			int gwidth = goal.get(i).getWidth();
			int gheight = goal.get(i).getHeight();
			for (int j = 0; j < current.size(); j++) {
				if (current.get(j).getWidth() == gwidth && current.get(j).getHeight() == gheight) {
					int tempScore = (int) Math.abs((current.get(j).getCol() - goal.get(i).getCol()));
					tempScore = tempScore + (int) Math.abs((current.get(j).getRow() - goal.get(i).getRow()));
					if (tempScore < scores[i] && !blockUsed.contains(j)) {
						scores[i] = tempScore;
						blockUsed.add(j);
					}
				}
				scores[i] = scores[i] * gheight * gwidth;
			}
		}
		int totalScore = 0;
		for (int l = 0; l < scores.length; l ++) {
			totalScore = totalScore + scores[l];
		}
		return totalScore;
	}
	
	public int compareTo (Tray compareTray) {
		// Implementing the comparable interface.
		return Math.round(compareTray.myScore - myScore);
	}
	
    public String toString () {
    	// Hash code toString method that prints out the information for each block.
    	// (Top left row, top left column, height of block, width of block)
        String string = "";
        for (int i = 0; i < myBlocks.size(); i++) {
        	Block currBlock = myBlocks.get(i);
        	string = string + currBlock.getRow() + " " + currBlock.getCol() + " " 
        				    + currBlock.getHeight() + " " + currBlock.getWidth() + "\n";
        }     
        return string;
    }
    
    public boolean equals(Object obj){
        // Override object equals method by checking if Strings are the same
        // Every Tray + block configuration should be different if valid Tray
        return this.myBlocks.toString() == ((Tray)obj).myBlocks.toString();
    }

}
