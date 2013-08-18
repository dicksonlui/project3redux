import java.util.*;

/**
 * Solver class. Solves sliding block puzzles.
 * @author Dickson Lui, Jesse Luo
 */

public class Solver {
	
	private String debugOption;
	private Tray myStart;
	private Tray myGoal;
	private boolean printMoves = false;
	private boolean printNumber = false;
	private int numberofMoves = 0;
	private boolean runtime = false;
	
	PriorityQueue<Tray> fringe;
	HashSet<Tray> previousConfigs;
	
	public Solver (String debug, Tray start, Tray end) {
		// Solver object with a debugging argument passed in.
		
		// Error handling: Checking if the debug argument starts with "-o"
		if (!debug.startsWith("-o")) {
			throw new IllegalArgumentException("Your debugging argument must start with -o followed by corresponding codes. For help, please use -ooptions as your debugging argument.");
		}

		// Instance Variables
		this.debugOption = debug;
		this.myStart = start;
		this.myGoal = end;

		// Checking the debugging options.
		String option = debugOption.substring(2);

		if (option.equals("options")) {
			System.out.println("Debugging Options:");
			System.out.println("***********************************");
			System.out.println("Your debugging options are:");
			System.out.println("m: Prints all of the moves that were considered.");
			System.out.println("n: Prints the number of moves taken.");
			System.out.println("r: Show the runtime of the solver");
			System.out.println("***********************************");
			System.out.println("How to use the debugging options:");
			System.out.println("Pass in debugging argument as follows:");
			System.out.println("If you want to print all of the moves considered,");
			System.out.println("the number of moves taken, and the runtime, simply pass in:");
			System.out.println("-omnr");
			System.out.println("The order of the letters following the o do no matter.");

		} else {	
			for (int i = 0; i < option.length(); i++) {
				String current = option.substring(i, i+1);
				if (current.equals("m")) {
					// How to handle printing all moves considered.
					printMoves = true;
				} if (current.equals("n")) {
					// How to handle printing all moves considered.
					printNumber = true;
				} if (current.equals("r")) {
					// How to handle runtime calculation.
					runtime = true;
				} else {
					System.out.println("No such debug option: " + current + ".");
				}
			}
		}
		
		fringe = new PriorityQueue<Tray>();
		previousConfigs = new HashSet<Tray>();
		fringe.add(start);
	}
	
	public Solver (Tray start, Tray end) {
		// Solver object withOUT a debugging argument passed in.
		this.debugOption = null;
		this.myStart = start;
		this.myGoal = end;
		fringe = new PriorityQueue<Tray>();
		previousConfigs = new HashSet<Tray>();
		fringe.add(start);
	}
	
	public void makeMove () {
		Tray popped = fringe.poll();
		
		// Checks if the popped board is complete.
		if (isDone(popped, myGoal)) {
			// DONE
		}
		
		// If the Tray has already been visited, ignore it and try again.
		while (previousConfigs.contains(popped)) {
			popped = fringe.poll();
		}
		
		// Once a new tray is found, add it to the previous configurations.
		previousConfigs.add(popped);
		
		// Generate all possible moves, assign scores, and add them to the queue.
		LinkedList<Tray> generatedMoves = popped.generateMoves();
		for (int i = 0; i < generatedMoves.size(); i++) {
			Tray currentTray = generatedMoves.get(i);
			currentTray.myScore = currentTray.score(currentTray.getBlocks(), myGoal.getBlocks());
			fringe.add(currentTray);
		}
	}
	
	
	public static void main (String [ ] args) {
		Solver mySolver = null;
		// Start the timer.
		long startTime = System.currentTimeMillis();
		
		TrayReader toSolve = new TrayReader(args);
		
		// No debugging argument.
		if (args.length == 2) {
			mySolver = new Solver(toSolve.startingTray(), toSolve.goalTray());
		}
		
		// Debugging argument passed in.
		else if (args.length == 3) {
			mySolver = new Solver(toSolve.debugOption(), toSolve.startingTray(), toSolve.goalTray());
		} else {
			System.err.println("Incorrect command line entries.");
		}
		
		// Start timer if user passed runtime argument.
		if (mySolver.runtime == true) {
			startTime = System.currentTimeMillis();
		}
		
		// Solve here please.
		
		
		// Print time if user passed runtime argument.
		if (mySolver.runtime == true) {
			System.out.println("Runtime: " + (System.currentTimeMillis() - startTime) + "ms");
		}

		if (mySolver.printNumber == true) {
			System.out.println("Number of moves: " + mySolver.numberofMoves);
		}

	}

}