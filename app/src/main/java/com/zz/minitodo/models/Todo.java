package com.zz.minitodo.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Todo implements Parcelable {
    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };
    public String text;
    public Date remindDate;

    public Todo(String text, Date remindDate) {
        this.text = text;
        this.remindDate = remindDate;
    }

    protected Todo(Parcel in) {
        text = in.readString();
        remindDate = new Date(in.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(text);
    }
}
