package org.parkpickup.domain;

import java.util.Set;

public class Park {
    private final String id;
    private final String name;
    private final Double lat;
    private final Double lng;
    private final Set<Present> peoplePresent;

    public Park(String id, String name, Double lat, Double lng, Set<Present> peoplePresent) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.peoplePresent = peoplePresent;
    }

    public String getId() {
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
        private final Long personId;
        private final Long lastModified;

        public Present(Long personId, Long lastModified) {
            this.personId = personId;
            this.lastModified = lastModified;
        }

        public Long getPersonId() {
            return personId;
        }

        public Long getLastModified() {
            return lastModified;
        }
    }
}
