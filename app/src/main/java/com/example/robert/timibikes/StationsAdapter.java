package com.example.robert.timibikes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
            TextView tvEmptySpots = (TextView) convertView.findViewById(R.id.tvEmptySpots);
            TextView tvMaximumNumberOfBikes = (TextView) convertView.findViewById(R.id.tvMaximumNumberOfBikes);
            TextView tvOcuppiedSpots = (TextView) convertView.findViewById(R.id.tvOcuppiedSpots);
            TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
            // Populate the data into the template view using the data object
            tvStationName.setText(station.StationName.replaceAll("^\"|\"$", ""));
            tvAddress.setText("Address: " + station.Address.replaceAll("^\"|\"$", ""));
            tvEmptySpots.setText("Empty: " + station.EmptySpots.replaceAll("^\"|\"$", ""));
            tvMaximumNumberOfBikes.setText("Max: " + station.MaximumNumberOfBikes.replaceAll("^\"|\"$", ""));
            tvOcuppiedSpots.setText("Occupied: " + station.OcuppiedSpots.replaceAll("^\"|\"$", ""));
            tvStatus.setText(station.Status.replaceAll("^\"|\"$", ""));
            if (station.Status.toLowerCase().replaceAll("^\"|\"$", "").equals("functionala")){
                tvStatus.setTextColor(Color.GREEN);
            } else {
                tvStatus.setTextColor(Color.RED);
            }


            // Return the completed view to render on screen
            return convertView;
        }
    }