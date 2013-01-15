package turboprt;

import gui.MainInterface;

/**
 * This is the starting point of the server program
 * @author Marcel
 */
public class TurboPRT {

	public static MainInterface gui = new MainInterface();
	
	public static void main(String[] args) {
		
		// Display the main UI
		TurboPRT.gui.setVisible(true);
		
		// Create thread for the tracker class.
		Tracker tracker = new Tracker();
		tracker.start();
		
		// Initialize the track
		Track.init();
	}
}
