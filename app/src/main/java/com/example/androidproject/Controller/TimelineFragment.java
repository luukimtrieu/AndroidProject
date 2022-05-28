package com.example.androidproject.Controller;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.Model.Database.AppDatabase;
import com.example.androidproject.Model.Database.DayStatus;
import com.example.androidproject.Model.DayDetailIcon;
import com.example.androidproject.Model.StringViewModel;
import com.example.androidproject.Model.TimelineItem;
import com.example.androidproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.net.URI;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BottomNavigationView bottomNavigationView;
    private Button btnDatePicker;
    private MonthYearPickerDialog monthYearPickerDialog;
    private AppDatabase db;
    private RecyclerView recyclerView;
    private TimelineAdapter timelineAdapter;
    private StringViewModel stringViewModel;
    private String yearMonth_string;


    public TimelineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimelineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimelineFragment newInstance(String param1, String param2) {
        TimelineFragment fragment = new TimelineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null)
        {
            db = Room.databaseBuilder(getContext(), AppDatabase.class, "database.db").allowMainThreadQueries().build();
            String date = getArguments().getString("date");

            stringViewModel = new ViewModelProvider(requireActivity()).get(StringViewModel.class);
            recyclerView = view.findViewById(R.id.recycleViewTimeline);
            btnDatePicker = view.findViewById(R.id.timelineDatePicker);
            initDatePicker();
            date = toMonthYear(date);
            btnDatePicker.setText(removeFirstCharZero(date));
            btnDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    monthYearPickerDialog.show(getParentFragmentManager(), "MonthYearPickerDialog");
                }
            });

            btnDatePicker.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String date = dateFormat(btnDatePicker.getText().toString());
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/yyyy");
                    YearMonth yearMonth = YearMonth.parse(date, dateFormat);
                    yearMonth_string = yearMonth.toString();
                    setData(yearMonth);
                }
            });

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/yyyy");
            YearMonth yearMonth = YearMonth.parse(date, dateFormat);
            yearMonth_string = yearMonth.toString();
            setData(yearMonth);


            bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setSelectedItemId(R.id.timelineFragment);
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.calendarFragment:
                            Navigation.findNavController(view).navigate(R.id.action_global_calendarFragment);
                            break;
                        case R.id.timelineFragment:
                            Navigation.findNavController(view).navigate(R.id.action_global_timelineFragment);
                            break;
                        case R.id.reportFragment:
                            Navigation.findNavController(view).navigate(R.id.action_global_reportFragment);
                            break;
                    }
                    return true;
                }
            });
        }
        else {
            db = Room.databaseBuilder(getContext(), AppDatabase.class, "database.db").allowMainThreadQueries().build();
            stringViewModel = new ViewModelProvider(requireActivity()).get(StringViewModel.class);
            recyclerView = view.findViewById(R.id.recycleViewTimeline);
            btnDatePicker = view.findViewById(R.id.timelineDatePicker);
            initDatePicker();
            btnDatePicker.setText(getTodayDate());
            btnDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    monthYearPickerDialog.show(getParentFragmentManager(), "MonthYearPickerDialog");
                }
            });

            btnDatePicker.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String date = dateFormat(btnDatePicker.getText().toString());
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/yyyy");
                    YearMonth yearMonth = YearMonth.parse(date, dateFormat);
                    yearMonth_string = yearMonth.toString();
                    setData(yearMonth);
                }
            });

            yearMonth_string = getDate();
            LocalDate localDate = LocalDate.now();
            YearMonth yearMonth = YearMonth.from(localDate);
            setData(yearMonth);

            bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setSelectedItemId(R.id.timelineFragment);
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.calendarFragment:
                            Navigation.findNavController(view).navigate(R.id.action_global_calendarFragment);
                            break;
                        case R.id.timelineFragment:
                            Navigation.findNavController(view).navigate(R.id.action_global_timelineFragment);
                            break;
                        case R.id.reportFragment:
                            Navigation.findNavController(view).navigate(R.id.action_global_reportFragment);
                            break;
                    }
                    return true;
                }
            });

        }
    }

    public String removeFirstCharZero(String date)
    {
        if(date.charAt(0) == '0')
            return date.substring(1);
        return date;
    }

    public String toMonthYear(String date)
    {
        String temp1 = date.substring(0,4);
        String temp2 = date.substring(5,7);
        return temp2 + "/" + temp1;
    }

    public String getDate()
    {
        String date = dateFormat(btnDatePicker.getText().toString());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth yearMonth = YearMonth.parse(date, dateFormat);
        return yearMonth.toString();
    }


    public void setData(YearMonth yearMonth)
    {
        List<DayStatus> dayStatuses = db.dayStatusDao().getAllBySelectedDate(yearMonth.toString());
        List<List<DayDetailIcon>> icons = getIconss(dayStatuses);
        List<TimelineItem> items = getItems(dayStatuses);
        timelineAdapter = new TimelineAdapter(getContext(), items, icons, yearMonth_string, getParentFragmentManager());
        recyclerView.setAdapter(timelineAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public String getTodayDate()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month += 1;
        return month + "/" + year;
    }

    public String dateFormat(String date)
    {
        if(date.charAt(1) == '/')
            return "0" + date;
        return date;
    }

    public void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 += 1;
                String date = makeDateString(i1, i);
                btnDatePicker.setText(date);
            }
        };

        monthYearPickerDialog = new MonthYearPickerDialog();
        monthYearPickerDialog.setListener(onDateSetListener);
    }

    public String makeDateString(int month, int year)
    {
        return month + "/" + year;
    }



    public List<TimelineItem> getItems(List<DayStatus> dayStatuses)
    {
        List<TimelineItem> items = new ArrayList<>();
        for(DayStatus dayStatus : dayStatuses)
        {
            TimelineItem item = new TimelineItem(new ImageView(getContext()), new TextView(getContext()), new TextView(getContext())
            , new ImageView(getContext()), new TextView(getContext()));
            if(dayStatus.emotion_type.equals("super happy"))
                item.getMainIcon().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.super_happy));
            if(dayStatus.emotion_type.equals("happy"))
                item.getMainIcon().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.happy));
            if(dayStatus.emotion_type.equals("no emotion"))
                item.getMainIcon().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.no_emotion));
            if(dayStatus.emotion_type.equals("sad"))
                item.getMainIcon().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.sad));
            if(dayStatus.emotion_type.equals("super sad"))
                item.getMainIcon().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.super_sad));

            item.getPhoto().setImageURI(Uri.parse(dayStatus.photo_URL));
            item.getNotes().setText(dayStatus.note);
            item.getDayOfWeek().setText(dayStatus.day_of_week);
            items.add(item);
        }
        return items;
    }

    public List<List<DayDetailIcon>> getIconss(List<DayStatus> dayStatuses)
    {
        List<List<DayDetailIcon>> iconss = new ArrayList<>();
        for(DayStatus dayStatus : dayStatuses)
        {
            List<DayDetailIcon> icons = new ArrayList<>();
            if(dayStatus.sunny){
                DayDetailIcon icon = new DayDetailIcon(new ImageView(getContext()));
                icon.getImageView().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.sunny));
                icons.add(icon);
            }
            if(dayStatus.windy){
                DayDetailIcon icon = new DayDetailIcon(new ImageView(getContext()));
                icon.getImageView().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.wind));
                icons.add(icon);
            }
            if(dayStatus.cloudy){
                DayDetailIcon icon = new DayDetailIcon(new ImageView(getContext()));
                icon.getImageView().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.cloudy));
                icons.add(icon);
            }
            if(dayStatus.rainy){
                DayDetailIcon icon = new DayDetailIcon(new ImageView(getContext()));
                icon.getImageView().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.rainy));
                icons.add(icon);
            }
            if(dayStatus.snowy){
                DayDetailIcon icon = new DayDetailIcon(new ImageView(getContext()));
                icon.getImageView().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.snowy));
                icons.add(icon);
            }
            if(dayStatus.friends){
                DayDetailIcon icon = new DayDetailIcon(new ImageView(getContext()));
                icon.getImageView().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.friend));
                icons.add(icon);
            }
            if(dayStatus.family){
                DayDetailIcon icon = new DayDetailIcon(new ImageView(getContext()));
                icon.getImageView().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.family));
                icons.add(icon);
            }
            if(dayStatus.GFBF){
                DayDetailIcon icon = new DayDetailIcon(new ImageView(getContext()));
                icon.getImageView().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.love));
                icons.add(icon);
            }
            if(dayStatus.acquaintance){
                DayDetailIcon icon = new DayDetailIcon(new ImageView(getContext()));
                icon.getImageView().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.acquaintance));
                icons.add(icon);
            }
            if(dayStatus.none){
                DayDetailIcon icon = new DayDetailIcon(new ImageView(getContext()));
                icon.getImageView().setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.none));
                icons.add(icon);
            }
            iconss.add(icons);
        }
        return iconss;
    }
}