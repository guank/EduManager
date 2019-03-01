package com.example.lunar.edumanager;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

public class Timestamp {
    private String timestampId;
    long creationDate;

    public Timestamp(){

    }

    public Timestamp(String timestampId) {
        this.timestampId = timestampId;
    }

    public String getTimestampId() {
        return timestampId;
    }

    public java.util.Map<String, String> getCreationDate() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public Long getCreationDateLong() {
        return creationDate;
    }
}
