package turboprt;

import bluetooth.BluetoothService;
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
	
	private BluetoothService btService;
	private boolean connected = false;

	public boolean connect()
	{
		try
		{
			System.out.println("Connecting to "+this.macAddress+"...");
			btService = new BluetoothService();
			if (btService.connectToDevice(this.macAddress, 1)) {
				System.out.println("Connected to "+this.macAddress);
				this.connected = true;
				return true;
			} else {
				System.out.println("Failed to connect to "+this.macAddress);
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println("Bluetooth error");
			return false;
		}
	}
	
	public void sendCommand(String command)
	{
		System.out.println("Sending command to "+this.macAddress+": "+command);
		btService.sendCommand(command);
	}
	
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

	public boolean isConnected() {
		return connected;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	
}
