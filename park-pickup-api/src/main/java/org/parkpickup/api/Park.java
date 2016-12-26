package org.parkpickup.api;

import java.util.Set;

public class Park {
    public final long id;
    public final String displayName;
    public final Set<Person> playingNow;

    public Park(long id, String displayName, Set<Person> playingNow) {
        this.id = id;
        this.displayName = displayName;
        this.playingNow = playingNow;
    }
}
