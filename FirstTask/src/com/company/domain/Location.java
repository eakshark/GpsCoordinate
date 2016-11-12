package com.company.domain;

import java.util.List;

/**
 * Created by Aleksandar on 11/11/2016.
 */
public class Location {

    private String name;

    private List<GpsPoint> gpsPoints;

    public Location(String name, List<GpsPoint> gpsPoints) {
        this.setName(name);
        this.setGpsPoints(gpsPoints);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public List<GpsPoint> getGpsPoints() {
        return gpsPoints;
    }

    private void setGpsPoints(List<GpsPoint> gpsPoints) {
        this.gpsPoints = gpsPoints;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getName());
        sb.append("\n");
        for (GpsPoint gpsPoint : gpsPoints) {
            sb.append(gpsPoint.toString());
            sb.append("\n");
        }
        return sb.toString();

    }
}
