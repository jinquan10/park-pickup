package org.parkpickup.api.exception;

public class InvalidFieldDetails {
    public final String fieldName;
    public final String msg;

    public InvalidFieldDetails() {
        this.fieldName = null;
        this.msg = null;
    }

    public InvalidFieldDetails(String fieldName, String msg) {
        this.fieldName = fieldName;
        this.msg = msg;
    }
}
