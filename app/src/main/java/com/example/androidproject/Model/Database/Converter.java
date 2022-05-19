package com.example.androidproject.Model.Database;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Converter {
    @TypeConverter
    public LocalDate toDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            return LocalDate.parse(dateString);
        }
    }

    @TypeConverter
    public String dateToTimestamp(LocalDate date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
}
