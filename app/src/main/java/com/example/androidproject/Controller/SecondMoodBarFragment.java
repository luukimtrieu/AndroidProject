package com.example.androidproject.Controller;

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
import android.widget.TextView;

import com.example.androidproject.Model.Database.AppDatabase;
import com.example.androidproject.Model.Database.DayStatus;
import com.example.androidproject.Model.StringViewModel;
import com.example.androidproject.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondMoodBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondMoodBarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PieChart pieChart;
    private AppDatabase db;
    private String year;
    private List<DayStatus> dayStatuses;
    private float super_happy_percent;
    private float happy_percent;
    private float no_emotion_percent;
    private float sad_percent;
    private float super_sad_percent;
    private double total;
    private TextView tv_super_happy_percentage;
    private TextView tv_happy_percentage;
    private TextView tv_no_emotion_percentage;
    private TextView tv_sad_percentage;
    private TextView tv_super_sad_percentage;

    public SecondMoodBarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondMoodBarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondMoodBarFragment newInstance(String param1, String param2) {
        SecondMoodBarFragment fragment = new SecondMoodBarFragment();
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
        return inflater.inflate(R.layout.fragment_second_mood_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_super_happy_percentage = view.findViewById(R.id.second_tvPercentageSuperHappy);
        tv_happy_percentage = view.findViewById(R.id.second_tvPercentageHappy);
        tv_no_emotion_percentage = view.findViewById(R.id.second_tvPercentageNoEmotion);
        tv_sad_percentage = view.findViewById(R.id.second_tvPercentageSad);
        tv_super_sad_percentage = view.findViewById(R.id.second_tvPercentageSuperSad);
        StringViewModel stringViewModel = new ViewModelProvider(requireActivity()).get(StringViewModel.class);
        stringViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                year = s.substring(0,4);
                db = Room.databaseBuilder(getContext(), AppDatabase.class, "database.db").allowMainThreadQueries().build();
                dayStatuses = db.dayStatusDao().getAllBySelectedDate(year);
                settingPieChart(view);
                setPieData();
            }
        });
    }

    public void settingPieChart(View view)
    {
        pieChart = view.findViewById(R.id.second_pieChart);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
    }

    public void setPieData()
    {
        List<PieEntry> entries = getEntries();
        List<Integer> colors = getColors();

        PieDataSet dataSet = new PieDataSet(entries,null);
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(0);

        pieChart.setData(pieData);
        pieChart.setEntryLabelTextSize(0);
        pieChart.getLegend().setEnabled(false);
        pieChart.invalidate();
        pieChart.animateX(1400, Easing.EaseInOutQuad);
    }

    public List<Integer> getColors()
    {
        List<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.super_happy, null));
        colors.add(getResources().getColor(R.color.happy, null));
        colors.add(getResources().getColor(R.color.no_emotion, null));
        colors.add(getResources().getColor(R.color.sad, null));
        colors.add(getResources().getColor(R.color.super_sad, null));
        return colors;
    }

    public List<PieEntry> getEntries()
    {
        List<PieEntry> entries = new ArrayList<>();
        total = db.dayStatusDao().countByDate(year);
        int super_happy_count = db.dayStatusDao().countEmotionTypeByDate("super happy", year);
        int happy_count = db.dayStatusDao().countEmotionTypeByDate("happy", year);
        int no_emotion_count = db.dayStatusDao().countEmotionTypeByDate("no emotion", year);
        int sad_count = db.dayStatusDao().countEmotionTypeByDate("sad", year);
        int super_sad_count = db.dayStatusDao().countEmotionTypeByDate("super sad", year);

        super_happy_percent = Math.round((super_happy_count / total) * 100);
        happy_percent = Math.round((happy_count / total) * 100);
        no_emotion_percent = Math.round((no_emotion_count / total) * 100);
        sad_percent = Math.round((sad_count / total) * 100);
        super_sad_percent = Math.round((super_sad_count / total) * 100);

        entries.add(new PieEntry(super_happy_percent));
        entries.add(new PieEntry(happy_percent));
        entries.add(new PieEntry(no_emotion_percent));
        entries.add(new PieEntry(sad_percent));
        entries.add(new PieEntry(super_sad_percent));

        tv_super_happy_percentage.setText((int)super_happy_percent + "%");
        tv_happy_percentage.setText((int)happy_percent + "%");
        tv_no_emotion_percentage.setText((int)no_emotion_percent + "%");
        tv_sad_percentage.setText((int)sad_percent + "%");
        tv_super_sad_percentage.setText((int)super_sad_percent + "%");

        return entries;
    }
}