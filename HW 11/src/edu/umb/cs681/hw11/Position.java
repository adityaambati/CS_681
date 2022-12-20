package edu.umb.cs681.hw11;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Position {

    private final double latitude;
    private final double longitude;
    private final double altitude;

    public Position(double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    @Override
    public String toString() {
        return "Position [altitude=" + altitude + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }

    public List<Double> getCoordinates() {
        return List.of(latitude, longitude, altitude);
    }

    public boolean equals(Position p) {
        return this.getCoordinates().equals(p.getCoordinates());
    }

    public AtomicReference<Position> change(double newLat, double newLon, double newAlt) {
        return new AtomicReference<>(new Position(newLat, newLon, newAlt));
    }

    public Position changeLatitude(double newLatitude) {
        return new Position(newLatitude, this.longitude, this.altitude);
    }

    public Position changeLongitude(double newLongitude) {
        return new Position(this.latitude, newLongitude, this.altitude);
    }

    public Position changeAltitude(double newAltitude) {
        return new Position(this.latitude, this.longitude, newAltitude);
    }

    public double distanceTo(Position anotherPosition) {
        double lat1 = this.latitude;
        double lon1 = this.longitude;
        double lat2 = anotherPosition.latitude;
        double lon2 = anotherPosition.longitude;
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

}
