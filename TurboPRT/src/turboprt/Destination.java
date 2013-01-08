package turboprt;

/**
 *
 * @author marcel
 */// deze class gebruiken wij om de destination van de podcar te bepalen.
  // de podcar geeft zijn location aan           
public class Destination {
        // type .....
	public static enum Type{REST, PASSENGER, GOAL};
	// location is waar de podcar zich vind
	private Location location;
	private Type type;
        // via de methodes get en setLocation kunnen de andere klassen deze atribuut gebruiken 
	public Location getLocation() {
		return location;
	}
        // de software sets de locatoin
	public void setLocation(Location location) {
		this.location = location;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
}
