package com.example.androidproject.Model.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
public class DayStatus {

    @PrimaryKey
    @NonNull
    public String date;
    public String day_of_week;
    public String emotion_type;
    public boolean sunny;
    public boolean cloudy;
    public boolean snowy;
    public boolean rainy;
    public boolean windy;
    public boolean friends;
    public boolean GFBF;
    public boolean acquaintance;
    public boolean none;
    public boolean family;
    public String note;
    public String photo_URL;


}


