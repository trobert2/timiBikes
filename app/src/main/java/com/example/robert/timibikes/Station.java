package com.example.robert.timibikes;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

/**
 * Created by Robert on 6/25/2015.
 */
public class Station {
    public PieChart chart;
    public String StationName;
    public String Address;
    public String OccupiedSpots;
    public String EmptySpots;
    public String MaximumNumberOfBikes;
    public String Status;
    public double Longitude;
    public double Latitude;

    public Station(String stationName, String address, String occupiedSpots, String emptySpots, String maximumNumberOfBikes, String status, double longitude, double latitude) {
        StationName = stationName;
        Address = address;
        OccupiedSpots = occupiedSpots;
        EmptySpots = emptySpots;
        MaximumNumberOfBikes = maximumNumberOfBikes;
        Status = status;
        Longitude = longitude;
        Latitude = latitude;
    }

    public PieChart setChart(PieChart chart) {
        PieDataSet pieDataSet;
        PieData pieData;
        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<String> labels = new ArrayList<String>();

        chart.setDrawHoleEnabled(true);
        chart.setHoleColorTransparent(true);
        chart.setDescription("");
        chart.setCenterText(String.format("%s / %s", this.EmptySpots, this.MaximumNumberOfBikes));

        labels.add("Ocupat");
        labels.add("Liber");

        entries.add(new Entry(Float.parseFloat(this.OccupiedSpots), 0));
        entries.add(new Entry(Float.parseFloat(this.EmptySpots), 1));

        pieDataSet = new PieDataSet(entries, "# de biciclete");

        pieDataSet.setColors(new int[] {Color.RED, Color.GREEN});

        pieData = new PieData(labels, pieDataSet);
        chart.setData(pieData);
        chart.getLegend().setEnabled(false);
        return chart;
    }
}
