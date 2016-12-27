package org.parkpickup.api;

import java.util.Set;

public class Park {
    public final long id;
    public final double centerLat;
    public final double centerLng;
    public final String displayName;
    public final Set<Person> playingNow;

    public Park(long id, double centerLat, double centerLng, String displayName, Set<Person> playingNow) {
        this.id = id;
        this.centerLat = centerLat;
        this.centerLng = centerLng;
        this.displayName = displayName;
        this.playingNow = playingNow;
    }
}
