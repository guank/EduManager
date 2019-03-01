package com.example.lunar.edumanager;

public class Clock {
    private String clockId;
    private String clockDate;
    private String clockIn;
    private String clockOut;

    public Clock(){

    }

    public Clock(String clockId, String clockDate, String clockIn, String clockOut) {
        this.clockId = clockId;
        this.clockDate = clockDate;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
    }

    public String getClockId() {
        return clockId;
    }

    public String getClockDate() {
        return clockDate;
    }

    public String getClockIn() {
        return clockIn;
    }

    public String getClockOut() {
        return clockOut;
    }
}
