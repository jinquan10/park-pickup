package org.parkpickup.api;

import java.util.ArrayList;
import java.util.List;

public class Person {
    public final String id;
    public final String displayName;
    public final List<ActivityEnum> activities;

    public Person() {
        this.id = null;
        this.displayName = null;
        this.activities = null;
    }

    // - For mobile client to send
    public Person(String id, String displayName, List<ActivityEnum> activities) {
        this.id = id;
        this.displayName = displayName;
        this.activities = activities;
    }

    // - For DB to return to mobile
    public Person(String id, String displayName, String activities) {
        this.id = id;
        this.displayName = displayName;
        this.activities = activities;
    }

    private List<ActivityEnum> stringToEnum(String activitiesString) {
        String[] activitiesArr = activitiesString.split(",");
        List<ActivityEnum> activities = new ArrayList<ActivityEnum>();

        for (String activity : activitiesArr) {
            activities.add(ActivityEnum.valueOf(activity));
        }
    }
}
