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
 * 
 *
 * @author Mark Blaas
 */
public class Intersection {
    public static final int numberOfIntersections = 14;
    
    private int[][] cost = new int[numberOfIntersections][numberOfIntersections];
    
    int ID;
    Location location = new Location();
    ArrayList<Intersection> connected;
    
    public Intersection(){
        
    }
    
    public Intersection(int ID, Location location){
        this.ID = ID;
        this.location = location;
    }
    
    public void addConnection(Intersection connection, int cost){
        this.connected.add(connection);
        this.cost[connection.ID][this.ID] = cost;
        this.cost[this.ID][connection.ID] = cost;
        
    }
    
}
