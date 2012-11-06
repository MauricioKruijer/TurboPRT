package turboprt;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcel
 */
public class Tracker extends Thread {
	private ArrayList<Podcar> podcars = new ArrayList<Podcar>();
	
	Tracker() {
		Podcar podcar;
		
		// Charmender
		podcar = new Podcar();
		podcar.setId(1);
		podcar.setMacAddress("0007809B2AF9");
		this.addPodcar(podcar);
		
		// N3liver
		podcar = new Podcar();
		podcar.setId(2);
		podcar.setMacAddress("00078096E0E1");
		this.addPodcar(podcar);
		
		// WT11-A
		podcar = new Podcar();
		podcar.setId(3);
		podcar.setMacAddress("0007804C4657");
		this.addPodcar(podcar);
		
		// Cyndaquil
//		podcar = new Podcar();
//		podcar.setId(4);
//		podcar.setMacAddress("abc");
//		this.addPodcar(podcar);
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
		while(true) {
			for(Podcar currentPodcar : this.getPodcars()) {
				// le magic
				currentPodcar.blinkIR(); // ofzo
				try {
					System.out.println(currentPodcar.getMacAddress());
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

}
