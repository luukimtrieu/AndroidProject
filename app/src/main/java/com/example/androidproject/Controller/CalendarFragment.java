package com.example.androidproject.Controller;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.androidproject.Model.CalendarItem;
import com.example.androidproject.Model.Database.AppDatabase;
import com.example.androidproject.Model.Database.DayStatus;
import com.example.androidproject.Model.SharedViewModel;
import com.example.androidproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private ArrayList<CalendarItem> calendarItems;
    private AppDatabase db;

    private CalendarAdapter calendarAdapter;
    private LocalDate selectedDate;
    private BottomNavigationView bottomNavigationView;
    private Button btnDatePicker;
    private MonthYearPickerDialog monthYearPickerDialog;
    private SharedViewModel viewModel;
    private List<DayStatus> dayStatuses;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDatePicker = view.findViewById(R.id.calendarDatePicker);
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
                LocalDate parsedDate = yearMonth.atDay(1);
                dayStatuses = db.dayStatusDao().getAllBySelectedDate(toYearMonth(date));
                setMonthYear(parsedDate);
            }
        });

        db = Room.databaseBuilder(getContext(), AppDatabase.class, "database.db").allowMainThreadQueries().build();

        String date = dateFormat(btnDatePicker.getText().toString());

        dayStatuses = db.dayStatusDao().getAllBySelectedDate(toYearMonth(date));

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        recyclerView = view.findViewById(R.id.RecycleViewSubIcons);
        selectedDate = LocalDate.now();
        setMonthYear(selectedDate);

        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.calendarFragment);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.calendarFragment:
                        Navigation.findNavController(view).navigate(R.id.action_global_calendarFragment);
                        break;
                    case R.id.timelineFragment:
                        Navigation.findNavController(view).navigate(R.id.action_global_timelineFragment);
                        break;
                    case R.id.reportFragment:
                        Navigation.findNavController(view).navigate(R.id.action_global_reportFragment);
                        break;
                    case R.id.settingFragment:
                        Navigation.findNavController(view).navigate(R.id.action_global_settingFragment);
                        break;
                }
                return true;
            }
        });
    }

    public String toYearMonth(String date)
    {
        String temp1 = date.substring(0,2);
        String temp2 = date.substring(3,7);
        return temp2 + "-" + temp1;
    }

    public String getDay(String date)
    {
        return date.substring(date.length() - 2);
    }

    public String dateFormat(String date)
    {
        if(date.charAt(1) == '/')
            return "0" + date;
        return date;
    }

    public String getTodayDate()
     {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month += 1;
        return month + "/" + year;
    }

    public void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1+= 1;
                String date = makeDateString(i1, i);
                btnDatePicker.setText(date);
            }
        };

        monthYearPickerDialog = new MonthYearPickerDialog();
        monthYearPickerDialog.setListener(onDateSetListener);
    }

    public String getMonthFormat(int month) {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "June";
        if(month == 7)
            return "July";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";
        return "Jan";
    }


    public String makeDateString(int month, int year)
    {
        return month + "/" + year;
    }


    public void setMonthYear(LocalDate selectedDate)
    {
        List<CalendarItem> daysInMonth = daysInMonthArray(selectedDate);

        calendarAdapter = new CalendarAdapter(daysInMonth, getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(calendarAdapter);
    }

    public int compare(String date)
    {
        int s = -1;
        for(DayStatus status : dayStatuses)
        {
            s = s + 1;
            if(status.date.compareTo(date) == 0)
                return s;
        }
        return -1;
    }

    public boolean isThisMonth(YearMonth yearMonth)
    {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        month = month + 1;
        if(month < 10)
            return (year + "-0" + month).compareTo(yearMonth.toString()) == 0;
        return (year + "0" + month).compareTo(yearMonth.toString()) == 0;
    }

    private List<CalendarItem> daysInMonthArray(LocalDate selectedDate) {
        List<CalendarItem> daysInMonthArray = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(selectedDate);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate getToday = LocalDate.now();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        int today = getToday.getDayOfMonth();
        int daysBeforeAdded = 0;

        for(int i = 1; i <= 42; i++)
        {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysBeforeAdded += 1;
                TextView tv = new TextView(getContext());
                tv.setText("");

                Button button = new Button(getContext());
                button.setBackground(getResources().getDrawable(R.color.transparent, null));

                CalendarItem calendarItem = new CalendarItem(tv, button);
                daysInMonthArray.add(calendarItem);
            }
            else {
                String date;
                if(i - dayOfWeek < 10) {
                    date = yearMonth + "-0" + (i - dayOfWeek);
                } else
                    date = yearMonth + "-" + (i - dayOfWeek);
                if(compare(date) != -1)
                {
                    int index = compare(date);
                    DayStatus dayStatus = dayStatuses.get(index);
                    TextView tv = new TextView(getContext());
                    tv.setText(String.valueOf(i - dayOfWeek));
                    if(today == i - daysBeforeAdded && isThisMonth(yearMonth))
                        tv.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.round_textview_today_background));

                    Button button = new Button(getContext());
                    button.setContentDescription(yearMonth.toString());
                    button.setWidth(40);
                    button.setHeight(40);
                    if(dayStatus.emotion_type.compareTo("super happy") == 0)
                        button.setBackground(getResources().getDrawable(R.drawable.super_happy, null));
                    if(dayStatus.emotion_type.compareTo("happy") == 0)
                        button.setBackground(getResources().getDrawable(R.drawable.happy, null));
                    if(dayStatus.emotion_type.compareTo("no emotion") == 0)
                        button.setBackground(getResources().getDrawable(R.drawable.no_emotion, null));
                    if(dayStatus.emotion_type.compareTo("sad") == 0)
                        button.setBackground(getResources().getDrawable(R.drawable.sad, null));
                    if(dayStatus.emotion_type.compareTo("super sad") == 0)
                        button.setBackground(getResources().getDrawable(R.drawable.super_sad, null));
                    CalendarItem calendarItem = new CalendarItem(tv, button);
                    daysInMonthArray.add(calendarItem);
                }
                else
                {
                    TextView tv = new TextView(getContext());
                    tv.setText(String.valueOf(i - dayOfWeek));
                    if(today == i - daysBeforeAdded && isThisMonth(yearMonth))
                        tv.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.round_textview_today_background));

                    Button button = new Button(getContext());
                    button.setBackground(getResources().getDrawable(R.drawable.round_button, null));
                    button.setContentDescription(yearMonth.toString());
                    button.setWidth(40);
                    button.setHeight(40);

                    CalendarItem calendarItem = new CalendarItem(tv, button);
                    daysInMonthArray.add(calendarItem);
                }
            }
        }

        return daysInMonthArray;
    }


}