package turboprt;

import gui.MainInterface;

/**
 * This is the starting point of the program
 * @author Marcel
 */
public class TurboPRT {

	public static MainInterface gui = new MainInterface();
	
	public static void main(String[] args) {
		
		// Display the main UI
		TurboPRT.gui.setVisible(true);
		
		// Create thread for the tracker class.
		new Tracker().start();
	}
}
