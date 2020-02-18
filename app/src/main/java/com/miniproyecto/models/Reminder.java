package com.miniproyecto.models;

public class Reminder {
    public int year, month, day, hour, minute;
    public String description;

    public Reminder(int year, int month, int day, int hour, int minute, String description) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.description = description;
    }
}
