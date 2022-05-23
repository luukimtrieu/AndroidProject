package com.example.androidproject.Controller;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.androidproject.Model.Database.AppDatabase;
import com.example.androidproject.Model.Database.DayStatus;
import com.example.androidproject.Model.StringViewModel;
import com.example.androidproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnDatePicker;
    private MonthYearPickerDialog monthYearPickerDialog;
    private BottomNavigationView bottomNavigationView;
    private AppDatabase db;

    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
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
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "database.db").allowMainThreadQueries().build();
        StringViewModel viewModel = new ViewModelProvider(requireActivity()).get(StringViewModel.class);

        btnDatePicker = view.findViewById(R.id.reportDatePicker);
        initDatePicker();
        btnDatePicker.setText(getTodayDate());
        YearMonth yearMonth = toYearMonth(btnDatePicker.getText().toString());
        viewModel.setString(yearMonth.toString());
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
                viewModel.setString(yearMonth.toString());
            }
        });

        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.reportFragment);
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

    public YearMonth toYearMonth(String date)
    {
        date = dateFormat(date);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth yearMonth = YearMonth.parse(date, dateFormat);
        return yearMonth;
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
                i1+= 1;
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


}