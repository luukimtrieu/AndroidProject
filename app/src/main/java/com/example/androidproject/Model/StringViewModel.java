package com.example.androidproject.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StringViewModel extends ViewModel {
    private MutableLiveData<String> liveData = new MutableLiveData<>();
    private String string;

    public MutableLiveData<String> getLiveData() {
        return liveData;
    }

    public void setLiveData(MutableLiveData<String> liveData) {
        this.liveData = liveData;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
        liveData.setValue(this.string);
    }
}
