package com.example.lunar.edumanager;

public class Math {
    private String mathId;
    private String mathName;
    private String mathLname;
    private String mathLevel;
    private String mathTest;
    private String mathHW;
    private String mathDay;
    private String mathHour;
    private String mathHistory;
    private String mathNotes;

    public Math(){

    }

    public Math(String mathId, String mathName, String mathLname, String mathLevel, String mathTest, String mathHW, String mathDay, String mathHour, String mathHistory, String mathNotes) {
        this.mathId = mathId;
        this.mathName = mathName;
        this.mathLname = mathLname;
        this.mathLevel = mathLevel;
        this.mathTest = mathTest;
        this.mathHW = mathHW;
        this.mathDay = mathDay;
        this.mathHour = mathHour;
        this.mathHistory = mathHistory;
        this.mathNotes = mathNotes;
    }

    public String getMathId() {
        return mathId;
    }

    public String getMathName() {
        return mathName;
    }

    public String getMathLname() {
        return mathLname;
    }

    public String getMathLevel() {
        return mathLevel;
    }

    public String getMathTest() {
        return mathTest;
    }

    public String getMathHW() {
        return mathHW;
    }

    public String getMathDay() {
        return mathDay;
    }

    public String getMathHour() {
        return mathHour;
    }

    public String getMathHistory() {
        return mathHistory;
    }

    public String getMathNotes() {
        return mathNotes;
    }
}