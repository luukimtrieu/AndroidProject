package com.example.androidproject.Model.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {DayStatus.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DayStatusDao dayStatusDao();
}
