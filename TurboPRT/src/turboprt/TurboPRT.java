package turboprt;

import gui.MainInterface;

/**
 *
 * @author marcel
 */
public class TurboPRT {

	public static MainInterface gui = new MainInterface();
	
	public static void main(String[] args) {
		
		TurboPRT.gui.setVisible(true); //..
		
		// Create thread for the tracker class.
		Tracker tracker = new Tracker();
		tracker.start();

	}
}
