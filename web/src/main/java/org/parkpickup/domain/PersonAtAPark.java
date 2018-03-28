package org.parkpickup.domain;

public class PersonAtAPark {
    public final long parkId;
    public final String parkName;
    public final double centerLat;
    public final double centerLng;
    public final String deviceId;
    public final String personName;
    public final String activities;

    public PersonAtAPark(
            long parkId,
            String parkName,
            double centerLat,
            double centerLng,
            String deviceId,
            String personName,
            String activities) {
        this.parkId = parkId;
        this.parkName = parkName;
        this.centerLat = centerLat;
        this.centerLng = centerLng;
        this.deviceId = deviceId;
        this.personName = personName;
        this.activities = activities;
    }
}
