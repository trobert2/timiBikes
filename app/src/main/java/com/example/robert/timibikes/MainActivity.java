package com.example.robert.timibikes;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LoadMainActivity().execute();
        setContentView(R.layout.activity_main);
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
            default:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class LoadMainActivity extends AsyncTask<String,String,String> {
        ArrayList<Station> stations = new ArrayList<Station>();
        StringBuilder response  = new StringBuilder();

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


            return response.toString();

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try{
                JsonParser parser = new JsonParser();
                JsonObject o = (JsonObject)parser.parse(response.toString());

                for (JsonElement record:o.getAsJsonArray("Data")) {

                    Station station = new Station(((JsonObject) record).get("StationName").toString(),
                                                  ((JsonObject) record).get("Address").toString(),
                                                  ((JsonObject) record).get("OcuppiedSpots").toString(),
                                                  ((JsonObject) record).get("EmptySpots").toString(),
                                                  ((JsonObject) record).get("MaximumNumberOfBikes").toString(),
                                                  ((JsonObject) record).get("StatusType").toString());


                    stations.add(station);
                }

                TextView t  = (TextView) findViewById(R.id.FirstView);
                t.setText("Station info");

                ListView l = (ListView) findViewById(R.id.FirstListView);
                StationsAdapter adapter = new StationsAdapter(MainActivity.this, stations);
                l.setAdapter(adapter);

            } catch (Exception e){e.printStackTrace();}
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
