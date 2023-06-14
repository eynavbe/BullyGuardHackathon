package com.eynav.hackathonbullyguard;

public class Recording {
    String date;
    String hour;
    int level;
    int amountHearing;

    public Recording(String date, String hour, int level, int amountHearing){
        this.date = date;
        this.hour = hour;
        this.level = level;
        this.amountHearing = amountHearing;
    }

    public int getAmountHearing() {
        return amountHearing;
    }

    public int getLevel() {
        return level;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    @Override
    public String toString() {
        return "Recording{" +
                "date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", level=" + level +
                ", amountHearing=" + amountHearing +
                '}';
    }
}
