package com.example.androidproject.Model;

import android.widget.Button;
import android.widget.TextView;

public class CalendarItem {
    private TextView dayInMonth;
    private Button stateOfDay;

    public CalendarItem(TextView dayInMonth, Button stateOfDay) {
        this.dayInMonth = dayInMonth;
        this.stateOfDay = stateOfDay;
    }

    public TextView getDayInMonth() {
        return dayInMonth;
    }

    public void setDayInMonth(TextView dayInMonth) {
        this.dayInMonth = dayInMonth;
    }

    public Button getStateOfDay() {
        return stateOfDay;
    }

    public void setStateOfDay(Button stateOfDay) {
        this.stateOfDay = stateOfDay;
    }
}
