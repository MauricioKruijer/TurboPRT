package turboprt;

/**
 * This class represents a location indicated by latitude and longitude
 * @author Marcel
 */
public class Location {
	
	private int latitude, longitude;
	
	/**
	 * Constructor
	 * Initialize with lat & long
	 * @param latitude
	 * @param longitude 
	 */
	public Location(int latitude, int longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Empty constructor
	 */
	public Location(){
		this(0,0);
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Get the string representation for this object.
	 * @return 
	 */
	public String toString()
	{
		return getLatitude()+", "+getLongitude();
	}
}
