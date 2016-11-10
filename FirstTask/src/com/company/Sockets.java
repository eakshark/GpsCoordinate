package com.company;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgeo.proj4j.BasicCoordinateTransform;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.ProjCoordinate;
import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.OSRef;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Aleksandar on 11/2/2016.
 */
public class Sockets {
    public static void main(String[] args) throws IOException, JSONException {
        new Sockets().getGpsCordinates();
    }

    private static final String[] args = new String[]{

    };


    private void getGpsCordinates() throws IOException, JSONException {
        try (Socket socket = new Socket("gis.sofiyskavoda.bg", 6080);

             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {


            out.println("GET " + "http://gis.sofiyskavoda.bg:6080/arcgis/rest/services/InfrastructureAlerts/MapServer/2/query?d=1478099494585&f=json&where=ACTIVESTATUS%20%3D%20%27In%20Progress%27&returnGeometry=true&spatialRel=esriSpatialRelIntersects&outFields=*&outSR=102100" + " HTTP/1.0");
            out.println();

            // Read data from the server until we finish reading the document
            String s = null;
            String line = in.readLine();
            while (line != null) {
                System.out.println(line);
                s = line;
                line = in.readLine();
            }
            System.out.println(s);


            JSONObject jsonObj = new JSONObject(s);

            JSONArray o = jsonObj.getJSONArray("features");
            for (int i = 0; i < o.length(); i++) {
                JSONObject obj = o.getJSONObject(i);
                JSONObject atr = obj.getJSONObject("attributes");
                System.out.println(atr.getString("LOCATION"));
                JSONObject array = obj.getJSONObject("geometry");
                JSONArray array1 = array.getJSONArray("rings");

                JSONArray array2 = array1.getJSONArray(0);
                for (int j = 0; j < array2.length(); j++) {


                    JSONArray arr3 = array2.getJSONArray(j);
                    Double first = (Double) arr3.get(0);
                    Double second = (Double) arr3.get(1);

                    System.out.println(first);
                    System.out.println(second);
                    String projection3395 = "+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext  +no_defs";
                    CRSFactory crsFactory = new CRSFactory();
                    org.osgeo.proj4j.CoordinateReferenceSystem sourceSRS = crsFactory.createFromParameters(null,
                            projection3395);

                    String projection4326 = "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs";
                    org.osgeo.proj4j.CoordinateReferenceSystem targetSRS = crsFactory.createFromParameters(null,
                            projection4326);

                    CoordinateTransform transformation = new BasicCoordinateTransform(sourceSRS,
                            targetSRS);

                    ProjCoordinate result = new ProjCoordinate();
                    ProjCoordinate input = new ProjCoordinate(first,
                            second);
                    transformation.transform(input, result);

                    System.out.println(result.x + ", " + result.y);
                }
            }
        }
    }
}