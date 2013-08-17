import java.util.*;

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
	
	public int score (ArrayList<Block> current, ArrayList<Block> goal) {
		int [] scores = new int[goal.size()];
		ArrayList<Integer> blockUsed = new ArrayList<Integer>();
		
		for (int i = 0; i < goal.size(); i++) {
			// score will be reduced, but we want '<' to work properly. 
			// so we just input a very large number
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
        String string = "";
        for (int i = 0; i < myBlocks.size(); i++) {
        	Block currBlock = myBlocks.get(i);
        	string = string + currBlock.getRow() + " " + currBlock.getCol() + " " 
        				    + currBlock.getHeight() + " " + currBlock.getWidth();
        }     
        return string;
    }
    
    public boolean equals(Object obj){
        // Override object equals method by checking if Strings are the same
        // Every Tray + block configuration should be different if valid Tray
        return this.myBlocks.toString() == ((Tray)obj).myBlocks.toString();
    }

}
