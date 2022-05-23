package com.example.androidproject.Model;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TimelineItem {
    private ImageView mainIcon;
    private TextView straightLine;
    private RecyclerView subIcons;
    private TextView notes;
    private ImageView photo;
    private TextView dayOfWeek;

    public TimelineItem(ImageView mainIcon, TextView straightLine, TextView notes, ImageView photo, TextView dayOfWeek) {
        this.mainIcon = mainIcon;
        this.straightLine = straightLine;
        this.notes = notes;
        this.photo = photo;
        this.dayOfWeek = dayOfWeek;
    }

    public TextView getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(TextView dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public ImageView getMainIcon() {
        return mainIcon;
    }

    public void setMainIcon(ImageView mainIcon) {
        this.mainIcon = mainIcon;
    }

    public TextView getStraightLine() {
        return straightLine;
    }

    public void setStraightLine(TextView straightLine) {
        this.straightLine = straightLine;
    }

    public RecyclerView getSubIcons() {
        return subIcons;
    }

    public void setSubIcons(RecyclerView subIcons) {
        this.subIcons = subIcons;
    }

    public TextView getNotes() {
        return notes;
    }

    public void setNotes(TextView notes) {
        this.notes = notes;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }


}
