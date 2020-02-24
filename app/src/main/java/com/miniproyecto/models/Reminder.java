package com.miniproyecto.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Reminder implements Parcelable {
    public String description;
    public int month;
    public int year;
    public int day;
    public int hour;
    public int minutes;
    public String date;
    public String time;
    public int id;

    public Reminder(int year, int month, int day, int minutes, int hour, String description, int id) {
        this.id =  id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.minutes = minutes;
        this.hour = hour;
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        this.date = day + "-" + month + "-" + year;
        this.time = hour + ":" + minutes;
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(description);
    }

    public Reminder(Parcel parcel) {
        date = parcel.readString();
        time = parcel.readString();
        description = parcel.readString();
    }

    public static final Parcelable.Creator<Reminder> CREATOR = new Parcelable.Creator<Reminder>() {
        public Reminder createFromParcel(Parcel reminder) {
            return new Reminder(reminder);
        }

        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };
}
