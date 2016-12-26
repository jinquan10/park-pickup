package org.parkpickup.api;

public class Location {
    public final Double lat;
    public final Double lng;

    public Location() {
        this.lat = null;
        this.lng = null;
    }

    public Location(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
