package com.example.androidproject.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoodBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoodBarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoodBarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoodBarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoodBarFragment newInstance(String param1, String param2) {
        MoodBarFragment fragment = new MoodBarFragment();
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
        return inflater.inflate(R.layout.fragment_mood_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CreatePieData(view);
    }

    public void CreatePieData(View view)
    {
        PieChart pieChart = view.findViewById(R.id.pieChart);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);


        ArrayList<PieEntry> arrayList = new ArrayList<>();
        arrayList.add(new PieEntry(0.2f));
        arrayList.add(new PieEntry(0.3f));
        arrayList.add(new PieEntry(0.3f));
        arrayList.add(new PieEntry(0.1f));
        arrayList.add(new PieEntry(0.1f));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.super_happy, null));
        colors.add(getResources().getColor(R.color.happy, null));
        colors.add(getResources().getColor(R.color.no_emotion, null));
        colors.add(getResources().getColor(R.color.sad, null));
        colors.add(getResources().getColor(R.color.super_sad, null));

        PieDataSet dataSet = new PieDataSet(arrayList,null);
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(0);

        pieChart.setData(pieData);
        pieChart.setEntryLabelTextSize(0);

        pieChart.getLegend().setEnabled(false);


    }
}