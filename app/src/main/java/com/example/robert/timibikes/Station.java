package com.example.robert.timibikes;

/**
 * Created by Robert on 6/25/2015.
 */
public class Station {
   public String StationName;
   public String Address;
   public String OcuppiedSpots;
   public String EmptySpots;
   public String MaximumNumberOfBikes;
   public String Status;

    public Station(String StationName, String Address, String OcuppiedSpots, String EmptySpots, String MaximumNumberOfBikes, String Status) {
        this.StationName = StationName;
        this.Address = Address;
        this.OcuppiedSpots = OcuppiedSpots;
        this.EmptySpots = EmptySpots;
        this.MaximumNumberOfBikes = MaximumNumberOfBikes;
        this.Status = Status;
    }
}
