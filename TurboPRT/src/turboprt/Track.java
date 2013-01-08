package turboprt;

import java.util.ArrayList;

/**
 *
 * @author marcel
 */// deze klasse 
public class Track {
	private ArrayList<Destination> destinations = new ArrayList<Destination>();

	public ArrayList<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(ArrayList<Destination> destinations) {
		this.destinations = destinations;
	}
	
	public void addDestination(Destination dest) {
		this.destinations.add(dest);
	}
	
	public void removeDestination(Destination dest) {
		this.destinations.remove(dest);
	}	
}
