package turboprt;

import bluetooth.BluetoothService;
import bluetooth.StreamListener;
import java.util.ArrayList;

/**
 *
 * @author marcel
 */// 
public class Podcar {
	private int id;          // het systeem kent de podcar door deze id
	private String name;     //...
	private Location location; // het location van de podcar
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>(); // meerdere passengen kunnen samen in de car
	private ArrayList<Destination> destinations = new ArrayList<Destination>(); // de car can verschillende destination hebben
	private Status status;    // het status van car waar hij zich op dit momment vind
	private String macAddress;  //....
	
	public BluetoothService btService;  //..
	private boolean connected = false;  
	// mogelijke status van de car
	public enum Status {DRIVING, WAITING, CHARGING, BLOCKED, DISCONNECTED};
	
	private ArrayList<PodcarListener> listeners = new ArrayList<PodcarListener>();
        // id -1 betekent....
	Podcar()
	{
		this.id = -1;
		this.location = new Location();
	}
	// de car maakt verbinding via bluetooth met het systeem 
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
	// verbreekt de verbinding van de car 
	public void disconnect()
	{
		this.btService.disconnect();
		this.connected = false;
		
		setStatus(Status.DISCONNECTED); // zet de status op disconnected
		
		System.out.println(this.getName()+" got disconnected.");
	}
	// stuur de command naar de car als de car connected is
	public void sendCommand(String command)
	{
		if(this.connected == true)
		{
			System.out.println("Sending command to "+this.macAddress+": "+command);
			btService.sendCommand(command);
		}
                else // anders geeft deze message
		{
			System.out.println("Device is not connected!");
		}
	}
	// verwerk de command
	public void processCommand(String command)
	{
		System.out.println(""+command);
		if(command.equals("ATDRV") && this.getStatus() != Podcar.Status.BLOCKED) // als de car niet geblocked is
		{
			if(this.getStatus() == Podcar.Status.WAITING) // als de podcar available is 
			{
				this.setStatus(Podcar.Status.DRIVING); // laat de car rijden
			}
			else
			{
				this.setStatus(Podcar.Status.WAITING); // andres maak de car klaar voor een rijd
			}
		}
		else if(command.equals("E666"))  // als de command geljik aan E666 is 
		{
			this.setStatus(Podcar.Status.BLOCKED); // block de car
		}
		else if(command.equals("!E666")) // anders zet hem op WAITING status
		{
			this.setStatus(Podcar.Status.WAITING);
		}
	}
	// de car neemt een bocht
	public void turn(int direction) {
		// turn
	}
	//de infraredledje knippert
	public void blinkIR() {
		
	}
	
	//
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
	
	// geeft de ID van de car
	public int getId() {
		return id;
	}
        //set de ID van de car
	public void setId(int id) {
		this.id = id;
		broadcastChange();
	}
        // de name van de car
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		broadcastChange();
	}
	//location van de car
	public Location getLocation() {
		return location;
	}
        // ...
	public void setLocation(Location location) {
		this.location = location;
		broadcastChange();
	}
        // maak een list van passengers
	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}
        // set de passengers in de lijst
	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
		broadcastChange();
	}
        // maak een lijt can bestemmingen 
	public ArrayList<Destination> getDestinations() {
		return destinations;
	}
        // set de bestemmingen in de lijst
	public void setDestinations(ArrayList<Destination> destinations) {
		this.destinations = destinations;
		broadcastChange();
	}
	// geeft een nieuwe destination aan de car
	public void addDestination(Destination dest) {
		this.destinations.add(dest);
		broadcastChange();
	}
	// haal een destination van de ljist uit
        public void removeDestination(Destination dest) {
		this.destinations.remove(dest);
		broadcastChange();
	}
        // geeft true als de car aan het rijden is
	public boolean isDriving() {
		return (status == Status.DRIVING);
	}
	// geeft true als de car kan aangeroept worden
	public boolean isAvailable() {
		return (status != Status.DRIVING && status != Status.BLOCKED);
	}
        
	public Status getStatus()
	{
		return this.status;
	}
	// geeft de status van de car door een string
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
        // ..
	public String getMacAddress() {
		return macAddress;
	}
        // ..
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
		broadcastChange();
	}
	
	
}
