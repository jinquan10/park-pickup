package org.parkpickup.api;

public class Person {
    public final String id;
    public final String displayName;

    public Person() {
        this.id = null;
        this.displayName = null;
    }

    public Person(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }
}
