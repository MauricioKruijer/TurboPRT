package turboprt;

/**
 *
 * @author marcel
 */
public class Destination {

	public static enum Type{REST, PASSENGER, GOAL};
	
	private Location location;
	private Type type;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public String toString()
	{
		return getLocation()+" ("+getType()+")";
	}
}
