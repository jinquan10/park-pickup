package org.parkpickup.api;

import java.util.HashSet;
import java.util.Set;

public class Person {
    public final String id;
    public final String displayName;
    public final Set<ActivityEnum> activities;

    public Person() {
        this.id = null;
        this.displayName = null;
        this.activities = null;
    }

    // - For mobile client to send
    public Person(String id, String displayName, Set<ActivityEnum> activities) {
        this.id = id;
        this.displayName = displayName;
        this.activities = activities;
    }

    // - For DB to return to mobile
    public Person(String id, String displayName, String activities) {
        this.id = id;
        this.displayName = displayName;
        this.activities = stringToEnum(activities);
    }

    private Set<ActivityEnum> stringToEnum(String activitiesString) {
        String[] activitiesArr = activitiesString.split(",");
        Set<ActivityEnum> activities = new HashSet<>();

        for (String activity : activitiesArr) {
            activities.add(ActivityEnum.valueOf(activity));
        }

        return activities;
    }
}
