package turboprt;

import bluetooth.BluetoothService;
import java.util.ArrayList;

/**
 *
 * @author marcel
 */
public class Tracker extends Thread implements PodcarListener {

	private static ArrayList<Podcar> podcars = new ArrayList<Podcar>();
	private BluetoothService device = null;

	Tracker() {

		Podcar podcar;

		// Charmender
		podcar = new Podcar();
//		podcar.setId(1);
		podcar.setName("Charmender");
		podcar.setMacAddress("0007809B2AF9");
		this.addPodcar(podcar);

		// N3liver
		podcar = new Podcar();
//		podcar.setId(2);
		podcar.setName("N3liver");
		podcar.setMacAddress("00078096E0E1");
		this.addPodcar(podcar);

		// WT11-A
		podcar = new Podcar();
//		podcar.setId(3);
		podcar.setName("WT11-A");
		podcar.setMacAddress("0007804C4657");
		this.addPodcar(podcar);

		// Cyndaquil
		podcar = new Podcar();
//		podcar.setId(4);
		podcar.setName("Cyndaquil");
		podcar.setMacAddress("0007804C4730");
		this.addPodcar(podcar);
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
		
		for(final Podcar device : this.podcars)
		{
			// Register for podcar updates
			device.addListener(this);
			
			Thread thread = new Thread(){

				@Override
				public void run() {
					super.run();
					
					// Connect
					device.connect();
					
					if(device.isConnected())
					{
						// Add this new bot to the UI
						TurboPRT.gui.addRow(device);

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
	
	public static Podcar getPodcarById(int id) throws Exception
	{
		for(Podcar device : podcars)
		{
			if(device.getId() == id)
				return device;
		}
		
		throw new Exception("Unknown podcar");
	}

	/*
	 * Update UI on podcar data change
	 */
	@Override
	public void update(Podcar device) {
		if(device.isConnected())
			TurboPRT.gui.setDeviceLine(device);
	}
}
