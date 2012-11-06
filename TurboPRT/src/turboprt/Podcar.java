package turboprt;

import java.util.ArrayList;

/**
 *
 * @author marcel
 */
public class Podcar {
	private int id;
	private Location location;
	private ArrayList<Passenger> passengers;
	private ArrayList<Destination> destinations;
	private boolean driving;
	private String macAddress;

	public void turn(int direction) {
		// turn
	}
	
	public void blinkIR() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
	}

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

	public boolean isDriving() {
		return driving;
	}

	public void setDriving(boolean driving) {
		this.driving = driving;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	
}
