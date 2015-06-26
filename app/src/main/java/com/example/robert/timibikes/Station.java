package com.example.robert.timibikes;

/**
 * Created by Robert on 6/25/2015.
 */
public class Station {
    public String StationName;
    public String Address;
    public String OccupiedSpots;
    public String EmptySpots;
    public String MaximumNumberOfBikes;
    public String Status;
    public double Longitude;
    public double Latitude;

    public Station(String StationName, String Address, String OccupiedSpots, String EmptySpots, String MaximumNumberOfBikes, String Status, double Longitude, double Latitude) {
        this.StationName = StationName;
        this.Address = Address;
        this.OccupiedSpots = OccupiedSpots;
        this.EmptySpots = EmptySpots;
        this.MaximumNumberOfBikes = MaximumNumberOfBikes;
        this.Status = Status;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
    }
}
