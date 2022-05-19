package com.example.androidproject.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Model.DayDetailIcon;
import com.example.androidproject.Model.TimelineItem;
import com.example.androidproject.R;

import java.util.ArrayList;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TimelineItem> timelineItems;

    public TimelineAdapter(Context context, ArrayList<TimelineItem> timelineItems) {
        this.context = context;
        this.timelineItems = timelineItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_day_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimelineItem item = timelineItems.get(position);
        holder.mainIcon.setImageDrawable(item.getMainIcon().getDrawable());
        holder.dayMonth.setText(item.getDayMonth().getText().toString());
        holder.notes.setText(item.getNotes().getText().toString());
        holder.photo.setImageDrawable(item.getPhoto().getDrawable());

        ArrayList<DayDetailIcon> icons = getIcons();
        DayDetailIconAdapter adapter = new DayDetailIconAdapter(context, icons);

        holder.subIcons.setAdapter(adapter);
        holder.subIcons.setLayoutManager(new GridLayoutManager(context, 5));
    }

    @Override
    public int getItemCount() {
        return timelineItems.size();
    }

    public ArrayList<DayDetailIcon> getIcons()
    {
        ImageView imageView = new ImageView(context);

        imageView.setImageResource(R.drawable.happy);

        ArrayList<DayDetailIcon> icons = new ArrayList<>();
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));

        return icons;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView mainIcon;
        private TextView dayMonth;
        private TextView straightLine;
        private RecyclerView subIcons;
        private TextView notes;
        private ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainIcon = itemView.findViewById(R.id.ImageViewMainIcon);

            dayMonth = itemView.findViewById(R.id.textViewDayMonth);

            straightLine = itemView.findViewById(R.id.textViewStraightLine);

            subIcons = itemView.findViewById(R.id.RecycleViewSubIcons);

            notes = itemView.findViewById(R.id.textViewNote);

            photo = itemView.findViewById(R.id.imageViewPhoto);

        }
    }
}
