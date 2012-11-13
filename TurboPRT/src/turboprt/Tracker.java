package turboprt;

import bluetooth.BluetoothService;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcel
 */
public class Tracker extends Thread {

	private ArrayList<Podcar> podcars = new ArrayList<Podcar>();
	private BluetoothService device = null;

	Tracker() {

		Podcar podcar;

		// Charmender
		podcar = new Podcar();
		podcar.setId(1);
		podcar.setMacAddress("0007809B2AF9");
		//this.addPodcar(podcar);

		// N3liver
		podcar = new Podcar();
		podcar.setId(2);
		podcar.setMacAddress("00078096E0E1");
		//this.addPodcar(podcar);

		// WT11-A
		podcar = new Podcar();
		podcar.setId(3);
		podcar.setMacAddress("0007804C4657");
		//this.addPodcar(podcar);

		// Cyndaquil
		podcar = new Podcar();
		podcar.setId(4);
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
			Thread thread = new Thread(){

				@Override
				public void run() {
					super.run();
					
					device.connect();
					
					System.out.println(device.getId());
					
					if(device.isConnected())
					{
						try {
							// Heartbeat
							new Thread(){
								@Override
								public void run() {
									while (true) {
										try {
											device.sendCommand("ATL01L11L21L31L41L51L61L71");
											Thread.sleep(100);
											device.sendCommand("ATL00L10L20L30L40L50L60L70");
											Thread.sleep(1000);
										} catch (InterruptedException ex) {
											Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
										}
									}
								}
							}.start();

							// Connection beep
							device.sendCommand("nop");
							device.sendCommand("nop");
							device.sendCommand("ATn");

					
							// Soort van vierkantje rijden
							device.sendCommand("ATs\240r\240");
							Thread.sleep(1000);
							device.sendCommand("ATs\000r\240");
							Thread.sleep(650);
							device.sendCommand("ATs\240r\240");
							Thread.sleep(1000);
							device.sendCommand("ATs\000r\240");
							Thread.sleep(650);
							device.sendCommand("ATs\240r\240");
							Thread.sleep(1000);
							device.sendCommand("ATs\000r\240");
							Thread.sleep(650);
							device.sendCommand("ATs\240r\240");
							Thread.sleep(1000);
							device.sendCommand("ATs\000r\240");
							Thread.sleep(650);
							device.sendCommand("ATs\240r\240");
							device.sendCommand("ATs\000r\000");
							
							
							device.sendCommand("ATN");
							
						} catch (InterruptedException ex) {
							Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				}
			};
			
			thread.start();
			threadArray.add(thread);
		}

	}
}
