package org.parkpickup.api;

public class Person {
    public final String id;
    public final String displayName;
    public final String activities;

    public Person() {
        this.id = null;
        this.displayName = null;
        this.activities = null;
    }

    public Person(String id, String displayName, String activities) {
        this.id = id;
        this.displayName = displayName;
        this.activities = activities;
    }
}
