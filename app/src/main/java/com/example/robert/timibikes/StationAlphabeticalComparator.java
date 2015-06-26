package com.example.robert.timibikes;

import java.util.Comparator;

/**
 * Created by Robert on 6/26/2015.
 */
public class StationAlphabeticalComparator implements Comparator<Station> {
        @Override
        public int compare(Station o1, Station o2) {
            return o1.StationName.compareTo(o2.StationName);
        }
}
