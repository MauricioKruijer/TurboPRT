/*
 * Copyright 2013 Mark Blaas. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY WARRANTIES ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 */
package turboprt;

import java.util.ArrayList;
import turboprt.Location;
/**
 * This class represents an intersection on a track
 * @author Mark Blaas
 */
public class Intersection {
    public static final int numberOfIntersections = 14;
    
    private int[][] cost = new int[numberOfIntersections][numberOfIntersections];
    
    private int ID;
    private Location location = new Location();
    private ArrayList<Intersection> connected = new ArrayList<Intersection>();
	private boolean isCalibrated = false;
    
    public Intersection(){
        
    }
    
    public Intersection(int ID, Location location){
        this.ID = ID;
        this.location = location;
    }

	public int[][] getCost() {
		return cost;
	}

	public void setCost(int[][] cost) {
		this.cost = cost;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
		isCalibrated = true;
	}

	public boolean isCalibrated() {
		return isCalibrated;
	}
	
    public void addConnection(Intersection connection, int cost){
        this.connected.add(connection);
        this.cost[connection.ID][this.ID] = cost;
        this.cost[this.ID][connection.ID] = cost;
    }
    
}
