package com.example.robert.timibikes;

import android.content.Context;

import java.util.Comparator;

/**
 * Created by Robert on 6/26/2015.
 */
public class StationLocationComparator  implements Comparator<Station> {
    public double myLatitude;
    public double myLongitude;

    StationLocationComparator(double myLatitude, double myLongitude){
        this.myLatitude = myLatitude;
        this.myLongitude = myLongitude;
    }

    public int compare(Station o1, Station o2) {

        float[] result1 = new float[3];
        android.location.Location.distanceBetween(myLatitude, myLongitude, o1.Latitude, o1.Longitude, result1);
        Float distance1 = result1[0];

        float[] result2 = new float[3];
        android.location.Location.distanceBetween(myLatitude, myLongitude, o2.Latitude, o2.Longitude, result2);
        Float distance2 = result2[0];

        return distance1.compareTo(distance2);
    }
}
