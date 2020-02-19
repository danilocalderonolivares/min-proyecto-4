package com.miniproyecto.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Reminder implements Parcelable {
    public String description;
    public String date;
    public String time;

    public Reminder(String date, String time, String description) {
        this.date = date;
        this.time = time;
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
