package com.movilizer.push;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public enum  EventType {
    CREATE, UPDATE, DELETE;

    public static EventType fromString(String string) {
        if(string == null) {
            throw new IllegalArgumentException("cannot convert null to event type");
        }

        string = string.toUpperCase();

        if(string.startsWith("C")) {
            return EventType.CREATE;
        }
        if(string.startsWith("U")) {
            return EventType.UPDATE;
        }
        if(string.startsWith("D")) {
            return EventType.DELETE;
        }

        throw new IllegalStateException("event type is not valid " + string);
    }

    }
