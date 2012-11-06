package turboprt;

/**
 *
 * @author marcel
 */
public class TurboPRT {

	public static void main(String[] args) {
		
		// Create thread for the tracker class.
		Tracker tracker = new Tracker();
		tracker.start();
	}
}
