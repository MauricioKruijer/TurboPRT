package turboprt;

import bluetooth.BluetoothService;
import java.util.ArrayList;

/**
 * This class monitors all of the ivibots
 * @author Marcel
 */
public class Tracker extends Thread implements PodcarListener {

	private static ArrayList<Podcar> podcars = new ArrayList<Podcar>();
	private BluetoothService device = null;

	/**
	 * Connect to the ivibots
	 */
	Tracker() {

		Podcar podcar;

		// Charmender
		podcar = new Podcar();
		podcar.setName("Charmender");
		podcar.setMacAddress("0007809B2AF9");
		this.addPodcar(podcar);

		// N3liver
		podcar = new Podcar();
		podcar.setName("N3liver");
		podcar.setMacAddress("00078096E0E1");
		this.addPodcar(podcar);

		// Pikachu
		podcar = new Podcar();
		podcar.setName("Pikachu");
		podcar.setMacAddress("0007804C4657");
		this.addPodcar(podcar);

		// Cyndaquil
		podcar = new Podcar();
		podcar.setName("Cyndaquil");
		podcar.setMacAddress("0007804C4730");
		this.addPodcar(podcar);
	}

	/**
	 * Get the current coordinates for an ivibot
	 * @param id
	 */
	public void locatePodcar(int id) {
		
	}

	/**
	 * Add a podcar to the list
	 * @param p 
	 */
	private void addPodcar(Podcar p) {
		this.podcars.add(p);
	}

	/**
	 * Remove a podcar from the list
	 * @param p 
	 */
	private void removePodcar(Podcar p) {
		this.podcars.remove(p);
	}

	/**
	 * Get the list of podcars
	 * @return ArrayList<Podcar>
	 */
	private ArrayList<Podcar> getPodcars() {
		return podcars;
	}

	/**
	 * Start the thread
	 */
	@Override
	public void run() {

		for(final Podcar device : this.podcars)
		{
			// Register for podcar updates
			device.addListener(this);
			
			// Create seperate threads for each ivibot
			new Thread(){
				@Override
				public void run() {
					super.run();
					
					// Connect
					device.connect();
					
					if(device.isConnected())
					{
						// Add this new bot to the UI
						TurboPRT.gui.addRow(device);

						// Send 2 nop commands so the bot can receive sequential commands
						device.sendCommand("nop");
						device.sendCommand("nop");
					}
				}
			}.start();  //..
		}
	}
	
	/**
	 * Get a podcar object by id
	 * @param id
	 * @return device
	 * @throws Exception 
	 */// geeft de bijbehorende car met de ID
	public static Podcar getPodcarById(int id) throws Exception
	{
		for(Podcar device : podcars)
		{
			if(device.getId() == id)
				return device;
		}
		
		throw new Exception("Unknown podcar");
	}

	/**
	 * Update UI on podcar data change
	 * @param device 
	 */
	@Override
	public void update(Podcar device) {
		if(device.isConnected())
			TurboPRT.gui.setDeviceLine(device);
	}
}
