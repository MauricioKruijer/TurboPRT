package turboprt;

import bluetooth.BluetoothService;
import java.util.ArrayList;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

/**
 *
 * @author marcel
 */
public class Tracker extends Thread implements PodcarListener {

	public static ArrayList<Podcar> podcars = new ArrayList<Podcar>();
	private BluetoothService device = null;
	private Wiimote wiimote;
	WiiTracker trackerThread = new WiiTracker();

	Tracker() {

		System.err.close();

		System.out.println("Connecting to wiimote");
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
		System.out.println("wiimotes found: " + wiimotes.length);
		wiimote = wiimotes[0];
		wiimote.activateIRTRacking();
		wiimote.addWiiMoteEventListeners(new WiiTracker());
		wiimote.addWiiMoteEventListeners(new Track());

		Podcar podcar;

		trackerThread.start();

		// Totodile
		podcar = new Podcar();
		podcar.setName("Totodile");
		podcar.setMacAddress("0007804C463D");
		this.addPodcar(podcar);
		TurboPRT.gui.addRow(podcar);

		// Chikorita
		podcar = new Podcar();
		podcar.setName("Chikorita");
		podcar.setMacAddress("0007804C4657");
		this.addPodcar(podcar);
		TurboPRT.gui.addRow(podcar);

		// Cyndaquil
		podcar = new Podcar();
		podcar.setName("Cyndaquil");
		podcar.setMacAddress("0007804C4730");
		this.addPodcar(podcar);
		TurboPRT.gui.addRow(podcar);
	}

	public void locatePodcar(int id) {
	}

	private void addPodcar(Podcar p) {
		this.podcars.add(p);
	}

	private void removePodcar(Podcar p) {
		this.podcars.remove(p);
	}

	private ArrayList<Podcar> getPodcars() {
		return podcars;
	}

	@Override
	public void run() {

		ArrayList<Thread> threadArray = new ArrayList<Thread>();

		for (final Podcar device : this.podcars) {
			// Register for podcar updates
			device.addListener(this);

			Thread thread = new Thread() {
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
			};

			thread.start();
			threadArray.add(thread);
		}
	}

	public static Podcar getPodcarById(int id) throws Exception {
		for (Podcar device : podcars) {
			if (device.getId() == id) {
				return device;
			}
		}

		throw new Exception("Unknown podcar");
	}

	/*
	 * Update UI on podcar data change
	 */
	@Override
	public void update(Podcar device) {
		if (device.isConnected()) {
			TurboPRT.gui.setDeviceLine(device);
		}
	}
}