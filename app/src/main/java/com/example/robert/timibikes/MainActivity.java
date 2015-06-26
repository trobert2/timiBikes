package com.example.robert.timibikes;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    public ArrayList<Station> stations = new ArrayList<Station>();
    public ListView listView;
    public TextView textView;
    public StationsAdapter adapter;
    public GPSTracker gps;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new StationsAdapter(MainActivity.this, stations);
        listView = (ListView) findViewById(R.id.FirstListView);
        textView = (TextView) findViewById(R.id.FirstView);
        gps = new GPSTracker(this);

        listView.setAdapter(adapter);
        new LoadMainActivity().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_update:
                new LoadMainActivity().execute();
                break;
            case R.id.action_alphabetical_order:
                Collections.sort(stations, new StationAlphabeticalComparator());
                adapter.notifyDataSetChanged();
                break;
            case R.id.action_location_order:
                double myLatitude = gps.getLatitude();
                double myLongitude = gps.getLongitude();
                Collections.sort(stations, new StationLocationComparator(myLatitude, myLongitude));
                adapter.notifyDataSetChanged();

            default:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class LoadMainActivity extends AsyncTask<String,String,String> {
        StringBuilder response  = new StringBuilder();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Updating...", Toast.LENGTH_SHORT).show();
            stations.clear();
        }

        @Override
        protected String doInBackground(String... arg0) {
            Map<String, String> map = new HashMap<String, String>();

            try {
                URL url = new URL("http://www.velotm.ro/Station/Read");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

                writer.write(getQuery(map));
                writer.flush();
                String line;
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                writer.close();
                reader.close();
                conn.connect();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            try{
                JsonParser parser = new JsonParser();
                JsonObject o = (JsonObject)parser.parse(response.toString());

                for (JsonElement record:o.getAsJsonArray("Data")) {

                    Station station = new Station(((JsonObject) record).get("StationName").toString(),
                            ((JsonObject) record).get("Address").toString(),
                            ((JsonObject) record).get("OcuppiedSpots").toString(),
                            ((JsonObject) record).get("EmptySpots").toString(),
                            ((JsonObject) record).get("MaximumNumberOfBikes").toString(),
                            ((JsonObject) record).get("StatusType").toString(),
                            Double.parseDouble(((JsonObject) record).get("Longitude").toString()),
                            Double.parseDouble(((JsonObject) record).get("Latitude").toString()));

                    stations.add(station);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            textView.setText(getString(R.string.last_updated) + simpleDateFormat.format(new Date()));

            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_LONG).show();
        }

        private String getQuery(Map<String, String> map)  throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (Map.Entry<String, String> entry : map.entrySet()) {

                if (first)
                    first = false;
                else
                    result.append("&");

                String key = entry.getKey();
                String value = entry.getValue();

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value, "UTF-8"));
            }
            //print here the result
            return result.toString();
        }
    }
}
