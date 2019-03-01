package com.example.lunar.edumanager;

public class English {
    private String englishId;
    private String englishName;
    private String englishLname;
    private String englishLevel;
    private String englishTest;
    private String englishHW;
    private String englishDay;
    private String englishHour;
    private String englishHistory;
    private String englishNotes;

    public English(){

    }

    public English(String englishId, String englishName, String englishLname, String englishLevel, String englishTest, String englishHW, String englishDay, String englishHour, String englishHistory, String englishNotes) {
        this.englishId = englishId;
        this.englishName = englishName;
        this.englishLname = englishLname;
        this.englishLevel = englishLevel;
        this.englishTest = englishTest;
        this.englishHW = englishHW;
        this.englishDay = englishDay;
        this.englishHour = englishHour;
        this.englishHistory = englishHistory;
        this.englishNotes = englishNotes;
    }

    public String getEnglishId() {
        return englishId;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getEnglishLname() {
        return englishLname;
    }

    public String getEnglishLevel() {
        return englishLevel;
    }

    public String getEnglishTest() {
        return englishTest;
    }

    public String getEnglishHW() {
        return englishHW;
    }

    public String getEnglishDay() {
        return englishDay;
    }

    public String getEnglishHour() {
        return englishHour;
    }

    public String getEnglishHistory() {
        return englishHistory;
    }

    public String getEnglishNotes() {
        return englishNotes;
    }
}
