package com.example.robert.timibikes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

/**
 * Created by Robert on 6/25/2015.
 */
public class StationsAdapter  extends ArrayAdapter<Station> {
        public StationsAdapter(Context context, ArrayList<Station> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Station station = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_station, parent, false);
            }
            // Lookup view for data population
            TextView tvStationName = (TextView) convertView.findViewById(R.id.tvStationName);
            TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
            TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
            // Populate the data into the template view using the data object
            tvStationName.setText(station.StationName.replaceAll("^\"|\"$", ""));
            tvAddress.setText("Address: " + station.Address.replaceAll("^\"|\"$", ""));
            String status = station.Status.toLowerCase().replaceAll("^\"|\"$", "");
            tvStatus.setText(status);

            if (status.equals("offline")){
                tvStatus.setTextColor(Color.RED);
            } else if (status.equals("subpopulated")){
                tvStatus.setTextColor(Color.GREEN);
            }
            PieChart chart = (PieChart) convertView.findViewById(R.id.chart_id);

            getStationChart(chart, station);


//          Return the completed view to render on screen
            return convertView;
        }

    private void getStationChart(PieChart chart, Station station){
        PieDataSet pieDataSet;
        PieData pieData;
        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<String> labels = new ArrayList<String>();

        chart.setDrawHoleEnabled(true);
        chart.setHoleColorTransparent(true);
        chart.setDescription("");
        chart.setCenterText(String.format("%s / %s", station.EmptySpots, station.MaximumNumberOfBikes));

        labels.add("Ocupat");
        labels.add("Liber");

        entries.add(new Entry(Float.parseFloat(station.OccupiedSpots), 0));
        entries.add(new Entry(Float.parseFloat(station.EmptySpots), 1));

        pieDataSet = new PieDataSet(entries, "# de biciclete");

        pieDataSet.setColors(new int[] {Color.RED, Color.GREEN});

        pieData = new PieData(labels, pieDataSet);
        chart.setData(pieData);
        chart.getLegend().setEnabled(false);
    }
    }