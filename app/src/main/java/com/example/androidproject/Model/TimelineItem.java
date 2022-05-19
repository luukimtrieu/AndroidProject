package com.example.androidproject.Model;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TimelineItem {
    private ImageView mainIcon;
    private TextView dayMonth;
    private TextView straightLine;
    private RecyclerView subIcons;
    private TextView notes;
    private ImageView photo;

    public ImageView getMainIcon() {
        return mainIcon;
    }

    public void setMainIcon(ImageView mainIcon) {
        this.mainIcon = mainIcon;
    }

    public TextView getDayMonth() {
        return dayMonth;
    }

    public void setDayMonth(TextView dayMonth) {
        this.dayMonth = dayMonth;
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

    public TimelineItem(ImageView mainIcon, TextView dayMonth, TextView straightLine, RecyclerView subIcons, TextView notes, ImageView photo) {
        this.mainIcon = mainIcon;
        this.dayMonth = dayMonth;
        this.straightLine = straightLine;
        this.subIcons = subIcons;
        this.notes = notes;
        this.photo = photo;
    }
}
