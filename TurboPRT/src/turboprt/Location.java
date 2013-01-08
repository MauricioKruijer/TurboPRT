package turboprt;

/**
 *
 * @author marcel
 */ //location is een bepaalde punt in de baan, dat heeft een Latitude en Longtitude waarde van type int.
public class Location {
	
	private int latitude, longitude;

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
}
