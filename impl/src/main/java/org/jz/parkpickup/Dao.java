package org.jz.parkpickup;

import java.util.Set;

public interface Dao {
    Set<PickupPlace> getPickupPlaces(double lat, double lng, int radiusMeters);
}
