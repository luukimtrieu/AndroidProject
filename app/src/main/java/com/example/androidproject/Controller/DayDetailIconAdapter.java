package com.example.androidproject.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Model.DayDetailIcon;
import com.example.androidproject.R;

import java.util.ArrayList;

public class DayDetailIconAdapter extends RecyclerView.Adapter<DayDetailIconAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DayDetailIcon> imageViews;

    public DayDetailIconAdapter(Context context, ArrayList<DayDetailIcon> imageViews) {
        this.context = context;
        this.imageViews = imageViews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.day_detail_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayDetailIcon icon = imageViews.get(position);
        holder.imageView.setImageDrawable(icon.getImageView().getDrawable());
    }

    @Override
    public int getItemCount() {
        return imageViews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewDayDetailIcon);
        }
    }
}
