package com.example.androidproject.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Database.DayStatus;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<DayStatus> liveDataDayStatus = new MutableLiveData<>();
    private DayStatus dayStatus;

    public MutableLiveData<DayStatus> getLiveDataDayStatus() {
        return liveDataDayStatus;
    }



    public DayStatus getDayStatus() {
        return dayStatus;
    }

    public void setDayStatus(DayStatus dayStatus) {
        this.dayStatus = dayStatus;
        liveDataDayStatus.setValue(this.dayStatus);
    }
}
