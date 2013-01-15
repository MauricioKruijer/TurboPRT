/*
 * Copyright 2013 Mark Blaas. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY WARRANTIES ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 */
package turboprt;

import java.util.logging.Level;
import java.util.logging.Logger;
import wiiusej.values.IRSource;
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
 * @author Mark Blaas
 */
class WiiTracker extends Thread implements WiimoteListener {

    int lastRequestedPodcar = 0;

    @Override
    public void onButtonsEvent(WiimoteButtonsEvent wbe) {
        //System.out.println("Button pressed");
    }

    @Override
    public void onIrEvent(IREvent ire) {
        Location loc = new Location();
        if (lastRequestedPodcar == -1) {
            return;
        }

        IRSource points[] = ire.getIRPoints();

        if (points.length == 1) {

            System.out.println("IR(" + lastRequestedPodcar + ": " + points[0].getX() + " | " + points[0].getY());
            
            loc.setLatitude(points[0].getX());
            loc.setLongitude(points[0].getY());


        } else if (points.length == 2) {
            try {
                Tracker.getPodcarById(Tracker.podcars.get(lastRequestedPodcar).getId()).sendCommand("#L00#L10");
                Tracker.podcars.get(lastRequestedPodcar).setIR(false);
                Thread.sleep(1000);
                if (points.length == 1) {
                    //Set passenger here
                    Location passengerLoc = new Location();
                    passengerLoc.setLatitude(points[0].getX());
                    passengerLoc.setLongitude(points[0].getY());
                    
                    Destination passengerDest = new Destination();
                    passengerDest.setType(Destination.Type.PASSENGER);
                    passengerDest.setLocation(passengerLoc);
                    
                    
                    boolean destAdded = false;
                    for (Podcar device : Tracker.podcars){
                        if (device.getStatus() == Podcar.Status.CHARGING){
                            device.addDestination(passengerDest);
                            destAdded = true;
                            System.out.println("dest added to " + device.getName());
                        }
                    }
                    if(!destAdded){
                        //No podcar available
                        System.out.println("No Podcar Available");
                    }
                    
                    Tracker.getPodcarById(Tracker.podcars.get(lastRequestedPodcar).getId()).sendCommand("#L01#L11");
                    Tracker.podcars.get(lastRequestedPodcar).setIR(true);
                }else{
                    //Takes to long...
                    System.out.println("IR interference...");
                }



            } catch (Exception ex) {
                Logger.getLogger(WiiTracker.class.getName()).log(Level.SEVERE, null, ex);
            }



        }



        if (Tracker.podcars.isEmpty()) {
            return;
        }

        try {
            Tracker.getPodcarById(Tracker.podcars.get(lastRequestedPodcar).getId()).setLocation(loc);

            // Turn off LED
            if (Tracker.podcars.get(lastRequestedPodcar).getIR()) {
                Thread.sleep(500);
                Tracker.getPodcarById(Tracker.podcars.get(lastRequestedPodcar).getId()).sendCommand("#L00#L10");
                Tracker.podcars.get(lastRequestedPodcar).setIR(false);
            }

            Thread.sleep(1000);
        } catch (Exception ex) {
            Logger.getLogger(WiiTracker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            for (Podcar p : Tracker.podcars) {
                System.out.println("Getting podcar " + p.getName());

                if (!p.isConnected()) {
                    System.out.println("... but is not connected. Break.");
                    continue;
                }
                System.out.println("... connected.");

                lastRequestedPodcar = p.getId();

                // Turn on LED
                p.sendCommand("#L01#L11");
                p.setIR(true);

                while (p.getIR()) {
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