package com.example.androidproject.Controller;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidproject.Model.Database.AppDatabase;
import com.example.androidproject.Model.Database.DayStatus;
import com.example.androidproject.Model.StringViewModel;
import com.example.androidproject.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoodFlowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoodFlowFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LineChart lineChart;
    private String yearMonth;
    private AppDatabase db;
    private List<DayStatus> dayStatuses;
    private int month;
    private int pos;

    public MoodFlowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     */
    // TODO: Rename and change types and number of parameters
    public static MoodFlowFragment newInstance(String param1, String param2) {
        MoodFlowFragment fragment = new MoodFlowFragment();
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
        return inflater.inflate(R.layout.fragment_moodflow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StringViewModel stringViewModel = new ViewModelProvider(requireActivity()).get(StringViewModel.class);
        stringViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                yearMonth = s;
                db = Room.databaseBuilder(getContext(), AppDatabase.class, "database.db").allowMainThreadQueries().build();
                dayStatuses = db.dayStatusDao().getAllBySelectedDate(yearMonth);
                lineChart = view.findViewById(R.id.lineChart);
                setValueFormat();
                settingLineChart();
                setLineData();
            }
        });
    }



    public void setLineData()
    {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<Entry> invisibleEntries = new ArrayList<>();

        Collections.sort(dayStatuses, new Comparator<DayStatus>() {
            @Override
            public int compare(DayStatus dayStatus, DayStatus t1) {
                return dayStatus.date.compareTo(t1.date);
            }
        });

        pos = 0;
        for(DayStatus dayStatus : dayStatuses)
        {
            boolean flag = false;
            int day = getDay(dayStatus.date) - 1;
            if(dayStatus.emotion_type.equals("super happy")) {
                flag = true;
                entries.add(new Entry(day, 5));
            }
            if(dayStatus.emotion_type.equals("happy")) {
                flag = true;
                entries.add(new Entry(day, 4));
            }
            if(dayStatus.emotion_type.equals("no emotion")) {
                flag = true;
                entries.add(new Entry(day, 3));
            }
            if(dayStatus.emotion_type.equals("sad")) {
                flag = true;
                entries.add(new Entry(day, 2));
            }
            if(dayStatus.emotion_type.equals("super sad")) {
                flag = true;
                entries.add(new Entry(day, 1));
            }
            if(!flag)
            {
                invisibleEntries.add(new Entry(pos, 0));
                pos += 1;
            }
        }

        while(pos < 31)
        {
            invisibleEntries.add(new Entry(pos, 0));
            pos += 1;
        }


        LineDataSet dataSet = new LineDataSet(entries, null);
        dataSet.setColor(getResources().getColor(R.color.color_line, null));
        dataSet.setCircleColor(getResources().getColor(R.color.color_line, null));
        dataSet.setDrawCircles(true);
        dataSet.setDrawCircleHole(true);
        dataSet.setLineWidth(3);
        dataSet.setCircleRadius(4);
        dataSet.setCircleHoleRadius(1.6f);
        dataSet.setValueTextSize(0);

        LineDataSet dataSet2 = new LineDataSet(invisibleEntries, null);
        dataSet2.setColor(getResources().getColor(R.color.transparent, null));
        dataSet2.setDrawCircles(true);
        dataSet2.setDrawCircleHole(true);
        dataSet2.setLineWidth(3);
        dataSet2.setCircleRadius(4);
        dataSet2.setCircleHoleRadius(1.6f);
        dataSet2.setValueTextSize(0);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        dataSets.add(dataSet2);

        LineData lineData = new LineData(dataSets);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    public ArrayList<Entry> createInvisibleEntryList()
    {
        ArrayList<Entry> entries = new ArrayList<>();
        for(int i = pos ; i <= 31; i ++)
        {
            entries.add(new Entry(i, 0));
        }
        return entries;
    }

    public ArrayList<Entry> createEntryList()
    {
        ArrayList<Entry> entries = new ArrayList<>();
        Collections.sort(dayStatuses, new Comparator<DayStatus>() {
            @Override
            public int compare(DayStatus dayStatus, DayStatus t1) {
                return dayStatus.date.compareTo(t1.date);
            }
        });

        pos = 0;
        for(DayStatus dayStatus : dayStatuses)
        {
            int i = getDay(dayStatus.date) - 1;
            if(dayStatus.emotion_type.equals("super happy"))
                entries.add(new Entry(i,5));
            if(dayStatus.emotion_type.equals("happy"))
                entries.add(new Entry(i,4));
            if(dayStatus.emotion_type.equals("no emotion"))
                entries.add(new Entry(i,3));
            if(dayStatus.emotion_type.equals("sad"))
                entries.add(new Entry(i,2));
            if(dayStatus.emotion_type.equals("super sad"))
                entries.add(new Entry(i,1));
            pos = pos + 1;
        }
        return entries;
    }

    public int getDay(String date)
    {
        return Integer.parseInt(date.substring(8));
    }

    public void settingLineChart()
    {
        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisLeft().setAxisMinimum(1);
        lineChart.getAxisLeft().setAxisMaximum(5);
        lineChart.getAxisLeft().setLabelCount(5);
        lineChart.getAxisLeft().setGranularity(1);

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getXAxis().setTextSize(14);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.getLegend().setEnabled(false);

    }

    public void setValueFormat()
    {
        YearMonth yearMonth = YearMonth.parse(this.yearMonth);
        month = yearMonth.getMonth().getValue();
        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if(month == 1 )
                {
                    String label = "";
                    if(value == 0)
                        label = "1/1";
                    if(value == 5)
                        label = "6/1";
                    if(value == 10)
                        label = "11/1";
                    if(value == 15)
                        label = "16/1";
                    if(value == 20)
                        label = "21/1";
                    if(value == 25)
                        label = "26/1";
                    if(value == 30)
                        label = "31/1";
                    return label;
                }
                if(month == 2 )
                {
                    String label = "";
                    if(value == 0)
                        label = "1/2";
                    if(value == 5)
                        label = "6/2";
                    if(value == 10)
                        label = "11/2";
                    if(value == 15)
                        label = "16/2";
                    if(value == 20)
                        label = "21/2";
                    if(value == 25)
                        label = "26/2";
                    if(value == 30)
                        label = "-";
                    return label;
                }
                if(month == 3 )
                {
                    String label = "";
                    if(value == 0)
                        label = "1/3";
                    if(value == 5)
                        label = "6/3";
                    if(value == 10)
                        label = "11/3";
                    if(value == 15)
                        label = "16/3";
                    if(value == 20)
                        label = "21/3";
                    if(value == 25)
                        label = "26/3";
                    if(value == 30)
                        label = "31/3";
                    return label;
                }
                if(month == 4 )
                {
                    String label = "";
                    if(value == 0)
                        label = "1/4";
                    if(value == 5)
                        label = "6/4";
                    if(value == 10)
                        label = "11/4";
                    if(value == 15)
                        label = "16/4";
                    if(value == 20)
                        label = "21/4";
                    if(value == 25)
                        label = "26/4";
                    if(value == 30)
                        label = "-";
                    return label;
                }
                if(month == 5 )
                {
                    String label = "";
                    if(value == 0)
                        label = "1/5";
                    if(value == 5)
                        label = "6/5";
                    if(value == 10)
                        label = "11/5";
                    if(value == 15)
                        label = "16/5";
                    if(value == 20)
                        label = "21/5";
                    if(value == 25)
                        label = "26/5";
                    if(value == 30)
                        label = "31/5";
                    return label;
                }
                if(month == 6 )
                {
                    String label = "";
                    if(value == 0)
                        label = "1/6";
                    if(value == 5)
                        label = "6/6";
                    if(value == 10)
                        label = "11/6";
                    if(value == 15)
                        label = "16/6";
                    if(value == 20)
                        label = "21/6";
                    if(value == 25)
                        label = "26/6";
                    if(value == 30)
                        label = "-";
                    return label;
                }
                if(month == 7 )
                {
                    String label = "";
                    if(value == 0)
                        label = "1/7";
                    if(value == 5)
                        label = "6/7";
                    if(value == 10)
                        label = "11/7";
                    if(value == 15)
                        label = "16/7";
                    if(value == 20)
                        label = "21/7";
                    if(value == 25)
                        label = "26/7";
                    if(value == 30)
                        label = "31/7";
                    return label;
                }
                if(month == 8 )
                {
                    String label = "";
                    if(value == 0)
                        label = "1/8";
                    if(value == 5)
                        label = "6/8";
                    if(value == 10)
                        label = "11/8";
                    if(value == 15)
                        label = "16/8";
                    if(value == 20)
                        label = "21/8";
                    if(value == 25)
                        label = "26/8";
                    if(value == 30)
                        label = "31/8";
                    return label;
                }
                if(month == 9 )
                {
                    String label = "";
                    if(value == 0)
                        label = "1/9";
                    if(value == 5)
                        label = "6/9";
                    if(value == 10)
                        label = "11/9";
                    if(value == 15)
                        label = "16/9";
                    if(value == 20)
                        label = "21/9";
                    if(value == 25)
                        label = "26/9";
                    if(value == 30)
                        label = "-";
                    return label;
                }
                if(month == 10 )
                {
                    String label = "";
                    if(value == 0)
                        label = "1/10";
                    if(value == 5)
                        label = "6/10";
                    if(value == 10)
                        label = "11/10";
                    if(value == 15)
                        label = "16/10";
                    if(value == 20)
                        label = "21/10";
                    if(value == 25)
                        label = "26/10";
                    if(value == 30)
                        label = "31/10";
                    return label;
                }
                if(month == 11 )
                {
                    String label = "";
                    if(value == 0)
                        label = "1/11";
                    if(value == 5)
                        label = "6/11";
                    if(value == 10)
                        label = "11/11";
                    if(value == 15)
                        label = "16/11";
                    if(value == 20)
                        label = "21/11";
                    if(value == 25)
                        label = "26/11";
                    if(value == 30)
                        label = "-";
                    return label;
                }
                if(month == 12 )
                {
                    String label = "";
                    if(value == 0)
                        label = "1/12";
                    if(value == 5)
                        label = "6/12";
                    if(value == 10)
                        label = "11/12";
                    if(value == 15)
                        label = "16/12";
                    if(value == 20)
                        label = "21/12";
                    if(value == 25)
                        label = "26/12";
                    if(value == 30)
                        label = "31/12";
                    return label;
                }
                return null;
            }
        });
    }
}