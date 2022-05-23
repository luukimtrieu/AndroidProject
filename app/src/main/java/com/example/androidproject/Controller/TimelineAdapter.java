package com.example.androidproject.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.androidproject.MainActivity;
import com.example.androidproject.Model.Database.AppDatabase;
import com.example.androidproject.Model.DayDetailIcon;
import com.example.androidproject.Model.StringViewModel;
import com.example.androidproject.Model.TimelineItem;
import com.example.androidproject.R;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private Context context;
    private List<TimelineItem> timelineItems;
    private List<List<DayDetailIcon>> iconss;
    public String date;
    private Bundle bundle;
    private FragmentManager fragment;

    public TimelineAdapter(Context context, List<TimelineItem> timelineItems, List<List<DayDetailIcon>> iconss, String date, FragmentManager fragment) {
        this.context = context;
        this.timelineItems = timelineItems;
        this.iconss = iconss;
        this.date = date;
        this.fragment = fragment;
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
        holder.dayOfWeek.setText(item.getDayOfWeek().getText().toString());
        holder.notes.setText(item.getNotes().getText().toString());
        holder.photo.setImageDrawable(item.getPhoto().getDrawable());


        List<DayDetailIcon> icons = iconss.get(position);
        DayDetailIconAdapter adapter = new DayDetailIconAdapter(context, icons);
        holder.subIcons.setAdapter(adapter);
        holder.subIcons.setLayoutManager(new GridLayoutManager(context, 5));
    }

    public String getDay(String day)
    {
        int index = day.indexOf(" ");
        if(index == 1)
            return day.substring(0,1);
        return day.substring(0,2);
    }


    @Override
    public int getItemCount() {
        return timelineItems.size();
    }

    public String dateFormat(String date)
    {
        if(date.charAt(1) == '/')
            return "0" + date;
        return date;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView mainIcon;
        private TextView dayOfWeek;
        private TextView straightLine;
        private RecyclerView subIcons;
        private TextView notes;
        private ImageView photo;
        private Button btnEdit;
        private Button btnDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainIcon = itemView.findViewById(R.id.ImageViewMainIcon);

            dayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);

            straightLine = itemView.findViewById(R.id.textViewStraightLine);

            subIcons = itemView.findViewById(R.id.RecycleViewSubIcons);

            notes = itemView.findViewById(R.id.textViewNote);

            photo = itemView.findViewById(R.id.imageViewPhoto);

            btnEdit = itemView.findViewById(R.id.btnEditDayDetail);

            btnDelete = itemView.findViewById(R.id.btnDeleteDayDetail);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bundle = new Bundle();
                    String day = getDay(dayOfWeek.getText().toString());
                    bundle.putBoolean("timeline", true);
                    bundle.putInt("exist", 1);
                    bundle.putString("day", day);
                    bundle.putString("year month", date);
                    Navigation.findNavController(view).navigate(R.id.action_timelineFragment_to_addDayStatusFragment, bundle);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, com.google.android.material.R.style.Base_V14_ThemeOverlay_MaterialComponents_MaterialAlertDialog);
                    builder.setTitle("Are you sure?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "database.db").allowMainThreadQueries().build();
                            String day_string;
                            int day = Integer.parseInt(getDay(dayOfWeek.getText().toString()));
                            if(day < 10)
                                day_string = "0" + day;
                            else
                                day_string = String.valueOf(day);

                            String fullDate = date + "-" + day_string;
                            db.dayStatusDao().deleteByDate(fullDate);
                            bundle = new Bundle();
                            bundle.putString("date", date);
                            Navigation.findNavController(view).navigate(R.id.action_timelineFragment_self, bundle);
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }
    }
}
