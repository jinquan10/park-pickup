package org.parkpickup.domain;

public class NearbyPopulatedParks {
    public final long parkId;
    public final String parkName;
    public final double centerLat;
    public final double centerLng;
    public final String deviceId;
    public final String personName;

    public NearbyPopulatedParks(long parkId, String parkName, double centerLat, double centerLng, String deviceId, String personName) {
        this.parkId = parkId;
        this.parkName = parkName;
        this.centerLat = centerLat;
        this.centerLng = centerLng;
        this.deviceId = deviceId;
        this.personName = personName;
    }
}
