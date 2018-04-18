package org.parkpickup.api.exception;

public class InvalidFieldDetails {
    public final String fieldName;
    public final String msg;

    public InvalidFieldDetails(String fieldName, String msg) {
        this.fieldName = fieldName;
        this.msg = msg;
    }
}
