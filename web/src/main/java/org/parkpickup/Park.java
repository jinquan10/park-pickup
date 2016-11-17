package org.parkpickup;

public class Park {
    private final String name;
    private final Double lat;
    private final Double lng;

    public Park(String name, Double lat, Double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
