package org.parkpickup.domain;

import java.util.Set;

public class Park {
    private final Long id;
    private final String name;
    private final Double lat;
    private final Double lng;
    private final Set<Present> peoplePresent;

    public Park(Long id, String name, Double lat, Double lng, Set<Present> peoplePresent) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.peoplePresent = peoplePresent;
    }

    public Long getId() {
        return id;
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

    public Set<Present> getPeoplePresent() {
        return peoplePresent;
    }

    public static class Present {
        private final Long deviceId;
        private final Long lastModified;

        public Present(Long deviceId, Long lastModified) {
            this.deviceId = deviceId;
            this.lastModified = lastModified;
        }

        public Long getDeviceId() {
            return deviceId;
        }

        public Long getLastModified() {
            return lastModified;
        }
    }
}
