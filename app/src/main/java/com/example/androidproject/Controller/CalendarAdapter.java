package com.example.androidproject.Controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Model.CalendarItem;
import com.example.androidproject.R;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private Bundle bundle;
    private List<CalendarItem> calendarItems;
    private Context context;

    public CalendarAdapter(List<CalendarItem> calendarItems, Context context) {
        this.calendarItems = calendarItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        CalendarItem item = calendarItems.get(position);
        holder.dayInMonth.setText(item.getDayInMonth().getText().toString());
        holder.dayInMonth.setBackground(item.getDayInMonth().getBackground());
        holder.stateOfDay.setBackground(item.getStateOfDay().getBackground());
        holder.stateOfDay.setContentDescription(item.getStateOfDay().getContentDescription());

    }

    @Override
    public int getItemCount() {
        return calendarItems.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder
    {
        private TextView dayInMonth;
        private Button stateOfDay;
        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            dayInMonth = itemView.findViewById(R.id.dayInMonth);
            stateOfDay = itemView.findViewById(R.id.stateOfDay);
            stateOfDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bundle = new Bundle();
                    //check existence of background
                    if(stateOfDay.getBackground().getConstantState() == itemView.getResources().getDrawable(R.color.transparent, null).getConstantState())
                    {
                        return;
                    }
                    else if(stateOfDay.getBackground().getConstantState()
                            == itemView.getResources().getDrawable(R.drawable.round_button, null).getConstantState())
                    {
                        //Get today date
                        LocalDate todayLocalDate = LocalDate.now();

                        //get selected date
                        String yearMonth_string = stateOfDay.getContentDescription().toString();
                        String day_string = dayInMonth.getText().toString();
                        if(Integer.parseInt(day_string) < 10)
                            day_string = "0" + day_string;
                        LocalDate selectedLocalDate = LocalDate.parse(yearMonth_string + "-" + day_string);
                        String day_of_week = selectedLocalDate.getDayOfWeek().toString();

                        //compare them
                        if(!selectedLocalDate.isAfter(todayLocalDate)){
                            bundle.putInt("exist", 0);
                            bundle.putString("day", dayInMonth.getText().toString());
                            bundle.putString("year month", stateOfDay.getContentDescription().toString());
                            bundle.putString("day of week", day_of_week);
                            Navigation.findNavController(view).navigate(R.id.action_calendarFragment_to_addDayStatusFragment, bundle);
                        }
                        else
                            Toast.makeText(context, "You can not select future date", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        bundle.putInt("exist", 1);
                        bundle.putString("day", dayInMonth.getText().toString());
                        bundle.putString("year month", stateOfDay.getContentDescription().toString());
                        Navigation.findNavController(view).navigate(R.id.action_calendarFragment_to_addDayStatusFragment, bundle);
                    }
                }
            });
        }
    }
}
