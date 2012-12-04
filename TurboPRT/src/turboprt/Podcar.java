package turboprt;

import bluetooth.BluetoothService;
import java.util.ArrayList;

/**
 *
 * @author marcel
 */
public class Podcar {
	private int id;
	private String name;
	private Location location;
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	private ArrayList<Destination> destinations = new ArrayList<Destination>();
	private Status status;
	private String macAddress;
	
	private BluetoothService btService;
	private boolean connected = false;
	
	public enum Status {DRIVING, WAITING, CHARGING};
	
	private ArrayList<PodcarListener> listeners = new ArrayList<PodcarListener>();

	Podcar()
	{
		this.location = new Location();
	}
	
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
			System.out.println("Couldn't connect to "+this.macAddress);
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
	
	
	public void addListener(PodcarListener listener)
	{
		this.listeners.add(listener);
	}
	public void removeListener(PodcarListener listener)
	{
		this.listeners.remove(listener);
	}
	
	private void broadcastChange()
	{
		for(PodcarListener listener : this.listeners)
		{
			listener.update(this);
		}
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		broadcastChange();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		broadcastChange();
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
		broadcastChange();
	}

	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
		broadcastChange();
	}

	public ArrayList<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(ArrayList<Destination> destinations) {
		this.destinations = destinations;
		broadcastChange();
	}
	
	public void addDestination(Destination dest) {
		this.destinations.add(dest);
		broadcastChange();
	}
	
	public void removeDestination(Destination dest) {
		this.destinations.remove(dest);
		broadcastChange();
	}

	public boolean isDriving() {
		return (status == Status.DRIVING);
	}

	public Status getStatus()
	{
		return this.status;
	}
	
	public String getStatusString()
	{
		if(status == Status.CHARGING)
		{
			return "Charging";
		}
		else if(status == Status.DRIVING)
		{
			return "Driving";
		}
		else
		{
			return "Waiting";
		}
	}
	
	public void setStatus(Status status) {
		this.status = status;
		broadcastChange();
	}

	public boolean isConnected() {
		return connected;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
		broadcastChange();
	}
	
	
}
