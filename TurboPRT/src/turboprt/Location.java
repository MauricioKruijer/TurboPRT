package turboprt;

/**
 *
 * @author marcel
 */
public class Location {
	
	private int latitude, longitude;
	
	public Location(int latitude, int longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
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
	
	public String toString()
	{
		return getLatitude()+", "+getLongitude();
	}
}
