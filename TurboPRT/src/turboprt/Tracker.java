package turboprt;

import bluetooth.BluetoothService;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

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
/**/
        // Charmender
        podcar = new Podcar();
//		podcar.setId(1);
        podcar.setName("Charmender");
        podcar.setMacAddress("0007809B2AF9");
        this.addPodcar(podcar);
    /*    
       
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
         */
        
         // WT-12A
         //podcar = new Podcar();
         //		podcar.setId(4);
         //podcar.setName("WT 12-A");
         //podcar.setMacAddress("00078096E0E1");
         //this.addPodcar(podcar);
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

/**
 * @TODO Verplaats dit naar een aparte file
 * @author Jeff
 */
class WiiTracker extends Thread implements WiimoteListener {

    int lastRequestedPodcar = 0;

    @Override
    public void onButtonsEvent(WiimoteButtonsEvent wbe) {
        //System.out.println("Button pressed");
    }

    @Override
    public void onIrEvent(IREvent ire) {
        if (lastRequestedPodcar == -1) {
            return;
        }

        System.out.println("IR(" + lastRequestedPodcar + ": " + ire.getX() + " | " + ire.getY());
        Location loc = new Location();
        loc.setLatitude(ire.getX());
        loc.setLongitude(ire.getY());


        if (Tracker.podcars.isEmpty()) {
            return;
        }

        try {
            Tracker.getPodcarById(Tracker.podcars.get(lastRequestedPodcar).getId()).setLocation(loc);

            // Turn off LED
            if(Tracker.podcars.get(lastRequestedPodcar).getIR()) {
                Thread.sleep(500);
                Tracker.getPodcarById(Tracker.podcars.get(lastRequestedPodcar).getId()).sendCommand("#L00#L10");
                Tracker.podcars.get(lastRequestedPodcar).setIR(false);
            }
            
            Thread.sleep(1000);
        } catch (Exception ex) {
            //Logger.getLogger(WiiTracker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            for (Podcar p : Tracker.podcars) {
				if(!p.isConnected()) continue;
				
                System.out.println("Getting podcar " + p.getName());
                lastRequestedPodcar = p.getId();

                // Turn on LED
                p.sendCommand("#L01#L11");
                p.setIR(true);
                
                while(p.getIR()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(WiiTracker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(WiiTracker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WiiTracker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void onMotionSensingEvent(MotionSensingEvent mse) {
    }

    @Override
    public void onExpansionEvent(ExpansionEvent ee) {
    }

    @Override
    public void onStatusEvent(StatusEvent se) {
    }

    @Override
    public void onDisconnectionEvent(DisconnectionEvent de) {
    }

    @Override
    public void onNunchukInsertedEvent(NunchukInsertedEvent nie) {
    }

    @Override
    public void onNunchukRemovedEvent(NunchukRemovedEvent nre) {
    }

    @Override
    public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent ghie) {
    }

    @Override
    public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent ghre) {
    }

    @Override
    public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent ccie) {
    }

    @Override
    public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent ccre) {
    }
}
