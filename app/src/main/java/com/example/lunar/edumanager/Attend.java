package com.example.lunar.edumanager;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.Map;

public class Attend {
    private String attendId;
    private String attendDate;
    private String attendSubject;
    //private HashMap<String, Object> dateCreated;
    long creationDate;

    public Attend(){

    }

    public Attend(String attendId, String attendDate, String attendSubject, Map<String, String> dateCreated) {
        this.attendId = attendId;
        this.attendDate = attendDate;
        this.attendSubject = attendSubject;
        //this.dateCreated = dateCreated;
    }

    public String getAttendId() {
        return attendId;
    }

    public String getAttendDate() {
        return attendDate;
    }

    public String getAttendSubject() {
        return attendSubject;
    }

    public java.util.Map<String, String> getCreationDate() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public Long getCreationDateLong() {
        return creationDate;
    }

/*
    public HashMap<String, Object> getDateCreated() {
        //If there is a dateCreated object already, then return that
        if (dateCreated != null) {
            return dateCreated;
        }
        //Otherwise make a new object set to ServerValue.TIMESTAMP
        HashMap<String, Object> dateCreatedObj = new HashMap<String, Object>();
        dateCreatedObj.put("date", ServerValue.TIMESTAMP);
        return dateCreatedObj;

    }

    @Exclude
    public long getDateCreatedLong() {
        return (long)dateCreated.get("date");
    }
*/


}
