package org.parkpickup.http;

import javax.validation.constraints.NotNull;

public class Location {
    @NotNull
    public final Double lat;

    @NotNull
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
