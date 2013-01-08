package turboprt;

import bluetooth.BluetoothService;
import bluetooth.StreamListener;
import java.util.ArrayList;

/**
 * This class represents an ivibot and it's functions
 * @author Marcel
 */
public class Podcar {
	private int id;
	private String name;
	private Location location;
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	private ArrayList<Destination> destinations = new ArrayList<Destination>();
	private Status status;
	private String macAddress;
	
	public BluetoothService btService;
	private boolean connected = false;  
	
	// Possible statusses for the podcar
	public enum Status {DRIVING, WAITING, CHARGING, BLOCKED, DISCONNECTED};
	
	private ArrayList<PodcarListener> listeners = new ArrayList<PodcarListener>();

	/**
	 * Constructor
	 * Initialize a new ivibot.
	 * Set the id to -1 so we know we still have to assign one.
	 */
	Podcar()
	{
		this.id = -1;
		this.location = new Location();
	}

	/**
	 * Connect to the podcar through bluetooth
	 * @return 
	 */
	public boolean connect()
	{
		try
		{
			System.out.println("Connecting to "+this.macAddress+"...");
			btService = new BluetoothService();
			if (btService.connectToDevice(this.macAddress, 1)) {
				System.out.println("Connected to "+this.macAddress);
				this.connected = true;
				this.status = Status.WAITING;
				
				// Start the listener for this ivibot
				new StreamListener(this);
				
				broadcastChange();
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
	
	/**
	 * Disconnect from the podcar
	 */
	public void disconnect()
	{
		this.btService.disconnect();
		this.connected = false;
		
		setStatus(Status.DISCONNECTED); // zet de status op disconnected
		
		System.out.println(this.getName()+" got disconnected.");
	}
	
	/**
	 * Send a command to the ivibot through bluetooth
	 * @param command 
	 */
	public void sendCommand(String command)
	{
		// Only try if the bot is connected
		if(this.connected == true)
		{
			System.out.println("Sending command to "+this.macAddress+": "+command);
			btService.sendCommand(command);
		}
		else
		{
			System.out.println("Device is not connected!");
		}
	}
	
	/**
	 * Process an incomming command
	 * @param command 
	 * @TODO fix this method
	 */
	public void processCommand(String command)
	{
		System.out.println(""+command);
	}

	/**
	 * Let this podcar blink it's IR so we know where it is.
	 * @TODO complete this method
	 */
	public void blinkIR() {
		
	}
	
	/**
	 * Add a listener for this podcar
	 * @param listener 
	 */
	public void addListener(PodcarListener listener)
	{
		this.listeners.add(listener);
	}
	/**
	 * Remove a listener for this podcar
	 * @param listener 
	 */
	public void removeListener(PodcarListener listener)
	{
		this.listeners.remove(listener);
	}
	
	/**
	 * Broadcast a change in this podcar.
	 * Call the update method for all of the listeners.
	 */
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

	/**
	 * Check if the podcar is currently driving
	 * @return 
	 */
	public boolean isDriving() {
		return (status == Status.DRIVING);
	}
	
	/**
	 * Check if the podcar is available
	 * @return 
	 */
	public boolean isAvailable() {
		return (status != Status.DRIVING && status != Status.BLOCKED);
	}
        
	public Status getStatus()
	{
		return this.status;
	}
	
	/**
	 * Return the status of the podcar as a string
	 * @return status
	 */
	public String getStatusString()
	{
		if(status == Status.CHARGING)
			return "Charging";
		else if(status == Status.DRIVING)
			return "Driving";
		else if(status == Status.BLOCKED)
			return "Blocked";
		else if(status == Status.DISCONNECTED)
			return "Disconnected";
		else
			return "Waiting";
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
