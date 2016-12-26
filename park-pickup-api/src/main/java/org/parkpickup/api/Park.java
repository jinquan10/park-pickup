package org.parkpickup.api;

import java.util.Set;

public class Park {
    public final long id;
    public final double lat;
    public final double lng;
    public final String displayName;
    public final Set<Person> playingNow;

    public Park(long id, double lat, double lng, String displayName, Set<Person> playingNow) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.displayName = displayName;
        this.playingNow = playingNow;
    }
}
