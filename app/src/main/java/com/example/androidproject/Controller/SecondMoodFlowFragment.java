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
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondMoodFlowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondMoodFlowFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LineChart lineChart;
    private String year;
    private AppDatabase db;
    private List<DayStatus> dayStatuses;
    private int pos;

    public SecondMoodFlowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondMoodFlowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondMoodFlowFragment newInstance(String param1, String param2) {
        SecondMoodFlowFragment fragment = new SecondMoodFlowFragment();
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
        return inflater.inflate(R.layout.fragment_second_mood_flow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StringViewModel stringViewModel = new ViewModelProvider(requireActivity()).get(StringViewModel.class);
        stringViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                year = s.substring(0,4);
                db = Room.databaseBuilder(getContext(), AppDatabase.class, "database.db").allowMainThreadQueries().build();
                dayStatuses = db.dayStatusDao().getAllBySelectedDate(year);
                lineChart = view.findViewById(R.id.second_lineChart );
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

        for(int i = 0; i <= 11; i++)
        {
            if(i + 1 < 10)
                dayStatuses = db.dayStatusDao().getAllBySelectedDate(year + "-0" + (i + 1));
            else
                dayStatuses = db.dayStatusDao().getAllBySelectedDate(year + "-" + (i + 1));
            float total = dayStatuses.size();
            float super_happy_count = 0;
            float happy_count = 0;
            float no_emotion_count = 0;
            float sad_count = 0;
            float super_sad_count = 0;

            for(DayStatus dayStatus : dayStatuses)
            {
                if(dayStatus.emotion_type.equals("super happy"))
                    super_happy_count += 5;
                if(dayStatus.emotion_type.equals("happy"))
                    happy_count += 4;
                if(dayStatus.emotion_type.equals("no emotion"))
                    no_emotion_count += 3;
                if(dayStatus.emotion_type.equals("sad"))
                    sad_count += 2;
                if(dayStatus.emotion_type.equals("super sad"))
                    super_sad_count += 1;
            }
            float average_emotion_point = (super_happy_count + happy_count + no_emotion_count + sad_count + super_sad_count) / total;
            if(total != 0)
                entries.add(new Entry(i,  average_emotion_point));
            else
                invisibleEntries.add(new Entry(i, 0));
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
        for(int i = pos ; i <= 12; i ++)
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
        for(int i = 0; i <= 11; i++)
        {
            float average_emotion_point = 0;
            if(i + 1 < 10)
                dayStatuses = db.dayStatusDao().getAllBySelectedDate(year + "-0" + (i + 1));
            else
                dayStatuses = db.dayStatusDao().getAllBySelectedDate(year + "-" + (i + 1));

            for(DayStatus dayStatus : dayStatuses)
            {
                if(dayStatus.emotion_type.equals("super happy"))
                    average_emotion_point += 5;
                if(dayStatus.emotion_type.equals("happy"))
                    average_emotion_point += 4;
                if(dayStatus.emotion_type.equals("no emotion"))
                    average_emotion_point += 3;
                if(dayStatus.emotion_type.equals("sad"))
                    average_emotion_point += 2;
                if(dayStatus.emotion_type.equals("super sad"))
                    average_emotion_point += 1;
            }
            if(average_emotion_point != 0)
                entries.add(new Entry(i, 1 + average_emotion_point / 30));
            pos = pos + 1;
        }

        return entries;
    }

    public void settingLineChart()
    {
        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisLeft().setAxisMinimum(1);
        lineChart.getAxisLeft().setAxisMaximum(5);
        lineChart.getAxisLeft().setLabelCount(5);
        lineChart.getAxisLeft().setGranularity(1);
        lineChart.getXAxis().setLabelCount(12, true);

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
        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                String label = "";
                if(value == 0)
                    label = "1";
                if(value == 1)
                    label = "2";
                if(value == 2)
                    label = "3";
                if(value == 3)
                    label = "4";
                if(value == 4)
                    label = "5";
                if(value == 5)
                    label = "6";
                if(value == 6)
                    label = "7";
                if(value == 7)
                    label = "8";
                if(value == 8)
                    label = "9";
                if(value == 9)
                    label = "10";
                if(value == 10)
                    label = "11";
                if(value == 11 )
                    label = "12";
                return label;
            }
        });
    }
}