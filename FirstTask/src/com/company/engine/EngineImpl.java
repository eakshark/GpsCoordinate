package com.company.engine;

import com.company.domain.Location;
import com.company.domain.GpsPoint;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgeo.proj4j.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandar on 11/11/2016.
 */
public class EngineImpl implements Engine {

    private static final String URL = "http://gis.sofiyskavoda.bg:6080/arcgis/rest/services/InfrastructureAlerts/MapServer/2/query?d=1478099494585&f=json&where=ACTIVESTATUS%20%3D%20%27In%20Progress%27&returnGeometry=true&spatialRel=esriSpatialRelIntersects&outFields=*&outSR=102100";

    private static final String HOST = "gis.sofiyskavoda.bg";

    private static final Integer PORT = 6080;
    @Override
    public void run() {
        try (Socket socket = new Socket(HOST, PORT);

             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {


            out.println("GET " + URL + " HTTP/1.0");
            out.println();

            String jsonString = null;
            String line = in.readLine();
            while (line != null) {
                jsonString = line;
                line = in.readLine();
            }


            JSONObject jsonObj = new JSONObject(jsonString);
            List<Location> locations = new ArrayList<>();

            JSONArray features = jsonObj.getJSONArray("features");
            for (int i = 0; i < features.length(); i++) {

                JSONObject currentFeature = features.getJSONObject(i);
                JSONObject atr = currentFeature.getJSONObject("attributes");


                JSONObject geometry = currentFeature.getJSONObject("geometry");

                List<GpsPoint> gpsPoints = new ArrayList<>();

                JSONArray rings = geometry.getJSONArray("rings");
                JSONArray ringsJSONArray = rings.getJSONArray(0);
                for (int j = 0; j < ringsJSONArray.length(); j++) {


                    JSONArray arr3 = ringsJSONArray.getJSONArray(j);
                    Double latInMeters = (Double) arr3.get(0);
                    Double lonInMeters = (Double) arr3.get(1);

                    GpsPoint gpsPoint = new GpsPoint(latInMeters,lonInMeters);
                    gpsPoints.add(gpsPoint);
                }
                Location location = new Location(atr.getString("LOCATION"), gpsPoints);
                locations.add(location);
            }

            print(locations);


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void print(List<Location> locations) {
        for (Location location : locations) {
            System.out.println(location.toString());
        }
    }
}
