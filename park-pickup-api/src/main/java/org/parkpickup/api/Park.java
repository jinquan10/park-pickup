package org.parkpickup.api;

import java.util.Set;

public class Park {
    public final long id;
    public final Object location;
    public final String displayName;
    public final Set<Person> playingNow;

    public Park(long id, Object location, String displayName, Set<Person> playingNow) {
        this.id = id;
        this.location = location;
        this.displayName = displayName;
        this.playingNow = playingNow;
    }
}
