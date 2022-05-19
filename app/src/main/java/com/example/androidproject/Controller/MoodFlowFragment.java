package com.example.androidproject.Controller;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidproject.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

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

        LineChart lineChart = view.findViewById(R.id.lineChart);
        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
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
        });

        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisLeft().setAxisMinimum(1);
        lineChart.getAxisLeft().setAxisMaximum(5);
        lineChart.getAxisLeft().setLabelCount(5);
        lineChart.getAxisLeft().setGranularity(1);
        lineChart.getXAxis().setGranularity(1);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getXAxis().setTextSize(14);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.getLegend().setEnabled(false);


        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(0,5));
        entries.add(new Entry(1,5));
        entries.add(new Entry(2,1));
        entries.add(new Entry(3,2));
        entries.add(new Entry(4,5));
        entries.add(new Entry(5,5));
        entries.add(new Entry(6,2));
        entries.add(new Entry(7,2));
        entries.add(new Entry(8,2));
        entries.add(new Entry(9,2));
        entries.add(new Entry(10,2));
        entries.add(new Entry(11,3));
        entries.add(new Entry(12,5));
        entries.add(new Entry(13,1));
        entries.add(new Entry(14,2));
        entries.add(new Entry(15,5));
        entries.add(new Entry(16,5));
        entries.add(new Entry(17,2));
        entries.add(new Entry(18,2));
        entries.add(new Entry(19,2));
        entries.add(new Entry(20,2));
        entries.add(new Entry(21,2));
        entries.add(new Entry(22,5));
        entries.add(new Entry(23,1));
        entries.add(new Entry(23,2));
        entries.add(new Entry(25,5));
        entries.add(new Entry(26,5));
        entries.add(new Entry(27,2));
        entries.add(new Entry(28,2));
        entries.add(new Entry(29,2));
        entries.add(new Entry(30,2));

        LineDataSet dataSet = new LineDataSet(entries, null);
        dataSet.setColor(getResources().getColor(R.color.color_line, null));
        dataSet.setCircleColor(getResources().getColor(R.color.color_line, null));
        dataSet.setDrawCircles(true);
        dataSet.setDrawCircleHole(true);
        dataSet.setLineWidth(3);
        dataSet.setCircleRadius(4);
        dataSet.setCircleHoleRadius(1.6f);
        dataSet.setValueTextSize(0);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        LineData lineData = new LineData(dataSets);

        lineChart.setData(lineData);



    }
}