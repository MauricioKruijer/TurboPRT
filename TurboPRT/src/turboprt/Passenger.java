package turboprt;

/**
 * This class represents a passenger which use the podcars
 * @author Marcel
 */
public class Passenger {
	private Location location;
	private Destination destination;
	/**
	 * The passenger enters it's destination
	 * @param dest
	 * @return 
	 */
	private boolean enterDestination(Destination dest) {
		return true;
	}

	/**
	 * The passenger changes it's destination
	 * @param dest
	 * @return 
	 */
	private boolean changeDestination(Destination dest) {
		return true;
	}
	
	/**
	 * The passenger enters the podcar
	 */
	public void enterPodcar() {
		
	}

	/**
	 * The passenger leaves the podcar
	 */
	public void leavePodcar() {
		
	}
	
	
	
}
