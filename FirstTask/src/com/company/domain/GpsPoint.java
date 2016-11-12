package com.company.domain;

import org.osgeo.proj4j.*;

/**
 * Created by Aleksandar on 11/11/2016.
 */
public class GpsPoint {

    private double latInMeters;

    private double longInMeters;

    private double lat;

    private double lon;

    public GpsPoint(double lat, double lon) {
        this.setLatInMeters(lat);
        this.setLongInMeters(lon);
        this.toDegrees(this.getLatInMeters(),this.getLongInMeters());
    }

    public double getLat() {
        return lat;
    }

    private void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    private void setLon(double lon) {
        this.lon = lon;
    }

    public double getLatInMeters() {
        return latInMeters;
    }

    private void setLatInMeters(double latInMeters) {
        this.latInMeters = latInMeters;
    }

    public double getLongInMeters() {
        return longInMeters;
    }

    private void setLongInMeters(double longInMeters) {
        this.longInMeters = longInMeters;
    }

    @Override
    public String toString() {
        return String.format("Latitude: %.6f, Longtitude: %.6f",this.getLat(),this.getLon());
    }

    private void toDegrees(double latInMeters, double lonInMeters) {

        String projection3395 = "+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext  +no_defs";
        CRSFactory crsFactory = new CRSFactory();
        CoordinateReferenceSystem sourceSRS = crsFactory.createFromParameters(null,
                projection3395);

        String projection4326 = "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs";
        CoordinateReferenceSystem targetSRS = crsFactory.createFromParameters(null,
                projection4326);

        CoordinateTransform transformation = new BasicCoordinateTransform(sourceSRS,
                targetSRS);

        ProjCoordinate result = new ProjCoordinate();
        ProjCoordinate input = new ProjCoordinate(latInMeters,
                lonInMeters);
        transformation.transform(input, result);

        this.setLat(result.y);
        this.setLon(result.x);
    }
}
