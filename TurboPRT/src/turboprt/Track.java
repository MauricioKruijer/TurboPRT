package turboprt;

import java.util.ArrayList;

/**
 * This class represents a track on which the ivibot drives.
 * @author Marcel
 */
public class Track {
	private ArrayList<Destination> destinations = new ArrayList<Destination>();

	/**
	 * Get the available destinations
	 * @return 
	 */
	public ArrayList<Destination> getDestinations() {
		return destinations;
	}

	/**
	 * Set the destinations for this track
	 * @param destinations 
	 */
	public void setDestinations(ArrayList<Destination> destinations) {
		this.destinations = destinations;
	}
	
	/**
	 * Add a destination
	 * @param dest 
	 */
	public void addDestination(Destination dest) {
		this.destinations.add(dest);
	}
	
	/**
	 * Remove a destination
	 * @param dest 
	 */
	public void removeDestination(Destination dest) {
		this.destinations.remove(dest);
	}	
}
