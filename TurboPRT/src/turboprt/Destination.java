package turboprt;

/**
 * This class represents a destination used by the podcars
 * @author Marcel
 */
public class Destination {
	public static enum Type {REST, PASSENGER, GOAL};
	private Location location;
	private Type type;
    
	/**
	 * Get the location for this destination
	 * @return location
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * Set the location for this destination
	 * @param location 
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Get the type of this destination
	 * @return type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Set the type of this destination
	 * @param type 
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
}
