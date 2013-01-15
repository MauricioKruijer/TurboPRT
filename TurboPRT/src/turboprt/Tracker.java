package turboprt;

import bluetooth.BluetoothService;
import java.util.ArrayList;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

/**
 * This class monitors all of the ivibots
 * @author Marcel
 */
public class Tracker extends Thread implements PodcarListener {

	public static ArrayList<Podcar> podcars = new ArrayList<Podcar>();
	private BluetoothService device = null;
	private Wiimote wiimote;
	WiiTracker trackerThread = new WiiTracker();

	/**
	 * Constructor
	 * Connect to the ivibots and wiimote
	 */
	Tracker() {

		// Disable error output (for the WiiUseJ library)
		System.err.close();

		// Connect to the wiimote, if found
		System.out.println("Connecting to wiimote");
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
		if(wiimotes.length > 0)
		{
			System.out.println("wiimotes found: " + wiimotes.length);
			wiimote = wiimotes[0];
			wiimote.activateIRTRacking();
			wiimote.addWiiMoteEventListeners(new WiiTracker());
			wiimote.addWiiMoteEventListeners(new Track());
			trackerThread.start();
		}

		Podcar podcar;

		// Totodile
		podcar = new Podcar();
		podcar.setName("Totodile");
		podcar.setMacAddress("0007804C463D");
		//this.addPodcar(podcar);
		//TurboPRT.gui.addRow(podcar);

		// Chikorita
		podcar = new Podcar();
		podcar.setName("Chikorita");
		podcar.setMacAddress("0007804C4657");
		//this.addPodcar(podcar);
		//TurboPRT.gui.addRow(podcar);

		// Cyndaquil
		podcar = new Podcar();
		podcar.setName("Cyndaquil");
		podcar.setMacAddress("0007804C4730");
		this.addPodcar(podcar);
		TurboPRT.gui.addRow(podcar);
		
		// Start threads for each podcar
		for(final Podcar device : this.podcars) {
			// Register for podcar updates
			device.addListener(this);
			
			new Thread() {
				@Override
				public void run() {
					super.run();

					// Connect
					device.connect();

					if (device.isConnected()) {
						// Stuur 2 BS commands zodat ie de rest wel pakt
						device.sendCommand("nop");
						device.sendCommand("nop");
					}
				}
			}.start();
		}
	}

	/**
	 * Add a podcar to the track
	 * @param p 
	 */
	private void addPodcar(Podcar p) {
		this.podcars.add(p);
	}

	/**
	 * Remove a podcar from the track
	 * @param p 
	 */
	private void removePodcar(Podcar p) {
		this.podcars.remove(p);
	}

	/**
	 * Get all podcars on the track.
	 * @return 
	 */
	private ArrayList<Podcar> getPodcars() {
		return podcars;
	}

	/**
	 * <<Thread>>
	 * Thread to continuesly locate the podcars.
	 */
	@Override
	public void run() {
		// Fill with wiitracker code
	}

	/**
	 * Get a podcar on the track by it's ID
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public static Podcar getPodcarById(int id) throws Exception {
		for (Podcar device : podcars) {
			if (device.getId() == id) {
				return device;
			}
		}

		throw new Exception("Unknown podcar");
	}

	/**
	 * Update UI on podcar data change
	 * @param device 
	 */
	@Override
	public void update(Podcar device) {
		if (device.isConnected()) {
			TurboPRT.gui.setDeviceLine(device);
		}
	}
}
