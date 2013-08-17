import java.util.Scanner;

public class TrayReader {
	
		private Tray startingTray;
		private Tray goalTray;
		private String debugOption;
		 
		// The first command-line argument, if specified, is the debugging option
	    // The second command-line argument(or first if no debugging) is the name of
	    // a file from which to read the starting Tray.
	    // The third command-line argument (or second if no debugging) is the name of
	    // a file from which to read the goal Tray.
	    
	    TrayReader (String [] args) {
	    	
	    	// Error handling: you can only pass in 2 or 3 arguments.
	        if (args.length > 3) {
	        	System.err.println ("*** bad Tray File: Cannot take more than 3 arguments.");
	            System.exit (1);
	        }
	        if (args.length < 2 ) {
	        	System.err.println ("*** bad Tray File: Need at least 2 arguments.");
	            System.exit (1);
	        }
	        
	        // If there are all three arguments, this implies there is a debugging argument at the front.
	    	if (args.length == 3) {
	    		this.debugOption=args[0];
	    		this.makeStartTray(args[1]);
	    		this.makeGoalTray(args[2]);
	    	}
	    	
	    	// If there is no debugging command at the beginning.
	    	if (args.length == 2){
	    		this.makeStartTray(args[0]);
	    		this.makeGoalTray(args[1]);
	    	}
	    }
	    
	    String debugOption ( ) {
	    	// Returns the debugging argument passed in by the user.
	    	return this.debugOption;
	    }
	    
	    Tray goalTray ( ) {
	    	// Returns the Goal Tray passed in.
	    	return this.goalTray;
	    }
	    
	    Tray startingTray ( ) {
	    	// Returns the Starting Tray passed in.
	    	return this.startingTray;
	    }
	    
	    void makeStartTray (String arg) {
	    	// Creates the first Tray state based on the .txt file name passed in.
	    	InputSource boardStart = new InputSource(arg);
	    	String curStartLine = boardStart.readLine();
	    	Scanner startLineScanner = new Scanner(curStartLine);
	    	int myLength = Integer.parseInt(startLineScanner.next());
	    	int myWidth = Integer.parseInt(startLineScanner.next());
	    	startLineScanner.close();
	    	// Initialize a Tray with the parsed length and width.
	    	startingTray = new Tray(myLength, myWidth);
	    	curStartLine = boardStart.readLine();
	    	while (curStartLine != null) {
	    		// Read each block and add each block to the tray.
	    		this.startingTray.addBlock(curStartLine);
	    		curStartLine = boardStart.readLine();
	    	}
	    }
	    
	    void makeGoalTray (String arg) {
	    	// Creates the goal Tray state based on the .txt file name passed in.
	    	InputSource boardGoal = new InputSource(arg);
	    	// Passes in length and width from the starting board.
	    	goalTray = new Tray(startingTray.getHeight(), startingTray.getWidth());
	    	String curGoalLine = boardGoal.readLine();
	    	while (curGoalLine != null) {
	    		// Adds each goal block to the goal board.
	    		goalTray.addBlock(curGoalLine);
	    		curGoalLine = boardGoal.readLine();
	    	}
	    }

	}