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
 * @author Mark Blaas
 */
public class TrackInitializer {

    int[][] locations = new int[Intersection.numberOfIntersections][Intersection.numberOfIntersections];
    int[][][] connections = {
        {{1, 5}, {4, 10}, {5, 10}},//Intersect0 connection {{from 0 to 1 cost 5},{from 0 to 4 cost 10}},
        {{0, 1}, {2, 1}, {6, 5}} //Intersect1 connections  {{from  1 to 0 cost 1}, {from 1 to 2 cost 1}}
    };

    public void init() {

        ArrayList<Intersection> intersections = new ArrayList<>();

        //Loop to create all intersections
        for (int i = 0; i < Intersection.numberOfIntersections; i++) {
            intersections.add(i, new Intersection(i, new Location(locations[i][0], locations[i][1])));
        }

        
        //Loop for creating connections between intersections...
        for (int i = 0; i < Intersection.numberOfIntersections; i++) { //For every intersection,
            for (int j = 0; j < connections.length; j++) { //For evry connection in connections[][][]
                //Add the connection and cost to the intersection object. 
                intersections.get(i).addConnection(intersections.get(connections[i][j][0]), connections[i][j][1]);
            }

        }
    }
}
