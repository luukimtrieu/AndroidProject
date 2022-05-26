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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.Model.Database.AppDatabase;
import com.example.androidproject.Model.Database.DayStatus;
import com.example.androidproject.Model.StringViewModel;
import com.example.androidproject.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IconRankingTopFiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IconRankingTopFiveFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView imageViewTop1;
    private ImageView imageViewTop2;
    private ImageView imageViewTop3;
    private ImageView imageViewTop4;
    private ImageView imageViewTop5;
    List<ImageView> imageViews;
    private TextView tvTop1Name;
    private TextView tvTop2Name;
    private TextView tvTop3Name;
    private TextView tvTop4Name;
    private TextView tvTop5Name;
    List<TextView> names;
    private TextView tvTop1Count;
    private TextView tvTop2Count;
    private TextView tvTop3Count;
    private TextView tvTop4Count;
    private TextView tvTop5Count;
    List<TextView> counts;
    private String yearMonth;
    private AppDatabase db;


    public IconRankingTopFiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IconRanking.
     */
    // TODO: Rename and change types and number of parameters
    public static IconRankingTopFiveFragment newInstance(String param1, String param2) {
        IconRankingTopFiveFragment fragment = new IconRankingTopFiveFragment();
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
        return inflater.inflate(R.layout.fragment_icon_ranking_top_five, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        StringViewModel stringViewModel = new ViewModelProvider(requireActivity()).get(StringViewModel.class);
        stringViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                yearMonth = s;
                db = Room.databaseBuilder(getContext(), AppDatabase.class, "database.db").allowMainThreadQueries().build();
                setTopFive();
            }
        });
    }

    public void initView(View view)
    {
        imageViews = new ArrayList<>();
        names = new ArrayList<>();
        counts = new ArrayList<>();

        imageViewTop1 = view.findViewById(R.id.imageViewTop1);
        imageViewTop2 = view.findViewById(R.id.imageViewTop2);
        imageViewTop3 = view.findViewById(R.id.imageViewTop3);
        imageViewTop4 = view.findViewById(R.id.imageViewTop4);
        imageViewTop5 = view.findViewById(R.id.imageViewTop5);
        imageViews.add(imageViewTop1);
        imageViews.add(imageViewTop2);
        imageViews.add(imageViewTop3);
        imageViews.add(imageViewTop4);
        imageViews.add(imageViewTop5);

        tvTop1Name = view.findViewById(R.id.textViewTop1_IconName);
        tvTop2Name = view.findViewById(R.id.textViewTop2_IconName);
        tvTop3Name = view.findViewById(R.id.textViewTop3_IconName);
        tvTop4Name = view.findViewById(R.id.textViewTop4_IconName);
        tvTop5Name = view.findViewById(R.id.textViewTop5_IconName);
        names.add(tvTop1Name);
        names.add(tvTop2Name);
        names.add(tvTop3Name);
        names.add(tvTop4Name);
        names.add(tvTop5Name);

        tvTop1Count = view.findViewById(R.id.textViewTop1Count);
        tvTop2Count = view.findViewById(R.id.textViewTop2Count);
        tvTop3Count = view.findViewById(R.id.textViewTop3Count);
        tvTop4Count = view.findViewById(R.id.textViewTop4Count);
        tvTop5Count = view.findViewById(R.id.textViewTop5Count);
        counts.add(tvTop1Count);
        counts.add(tvTop2Count);
        counts.add(tvTop3Count);
        counts.add(tvTop4Count);
        counts.add(tvTop5Count);
    }

    public void setTopFive()
    {
        List<Integer> integers = new ArrayList<>();

        int rainyCount = db.dayStatusDao().getRainyCount(yearMonth);
        integers.add(rainyCount);

        int sunnyCount = db.dayStatusDao().getSunnyCount(yearMonth);
        integers.add(sunnyCount);

        int windyCount = db.dayStatusDao().getWindyCount(yearMonth);
        integers.add(windyCount);

        int snowyCount = db.dayStatusDao().getSnowyCount(yearMonth);
        integers.add(snowyCount);

        int cloudyCount = db.dayStatusDao().getCloudyCount(yearMonth);
        integers.add(cloudyCount);

        int friendsCount = db.dayStatusDao().getFriendsCount(yearMonth);
        integers.add(friendsCount);

        int familyCount = db.dayStatusDao().getFamilyCount(yearMonth);
        integers.add(familyCount);

        int GFBFcount = db.dayStatusDao().getGFBFCount(yearMonth);
        integers.add(GFBFcount);

        int acquaintanceCount = db.dayStatusDao().getAcquaintanceCount(yearMonth);
        integers.add(acquaintanceCount);

        int noneCount = db.dayStatusDao().getNoneCount(yearMonth);
        integers.add(noneCount);
        integers.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                return t1.compareTo(integer);
            }
        });

        String emotion = "";
        for(int i = 0; i <= 4; i++)
        {
            int topCount = integers.get(i);
            if(topCount == rainyCount && !emotion.contains("rainy")) {
                imageViews.get(i).setImageResource(R.drawable.rainy);
                names.get(i).setText("Rainy");
                counts.get(i).setText(String.valueOf(topCount));
                emotion = emotion.concat(" rainy");
            }
            else if(topCount == sunnyCount && !emotion.contains("sunny")) {
                imageViews.get(i).setImageResource(R.drawable.sunny);
                names.get(i).setText("Sunny");
                counts.get(i).setText(String.valueOf(topCount));
                emotion = emotion.concat(" sunny");
            }
            else if(topCount == snowyCount && !emotion.contains("snowy")) {
                imageViews.get(i).setImageResource(R.drawable.snowy);
                names.get(i).setText("Snowy");
                counts.get(i).setText(String.valueOf(topCount));
                emotion = emotion.concat(" snowy");
            }
            else if(topCount == windyCount && !emotion.contains("windy")) {
                imageViews.get(i).setImageResource(R.drawable.wind);
                names.get(i).setText("Windy");
                counts.get(i).setText(String.valueOf(topCount));
                emotion = emotion.concat(" windy");
            }
            else if(topCount == cloudyCount && !emotion.contains("cloudy")) {
                imageViews.get(i).setImageResource(R.drawable.cloudy);
                names.get(i).setText("Cloudy");
                counts.get(i).setText(String.valueOf(topCount));
                emotion = emotion.concat(" cloudy");
            }
            else if(topCount == acquaintanceCount && !emotion.contains("acquaintance")) {
                imageViews.get(i).setImageResource(R.drawable.acquaintance);
                names.get(i).setText("Acquaintance");
                counts.get(i).setText(String.valueOf(topCount));
                emotion = emotion.concat(" acquaintance");
            }
            else if(topCount == GFBFcount && !emotion.contains("GFBF")) {
                imageViews.get(i).setImageResource(R.drawable.love);
                names.get(i).setText("GFBF");
                counts.get(i).setText(String.valueOf(topCount));
                emotion = emotion.concat(" GFBF");
            }
            else if(topCount == friendsCount && !emotion.contains("friends")) {
                imageViews.get(i).setImageResource(R.drawable.friend);
                names.get(i).setText("Friends");
                counts.get(i).setText(String.valueOf(topCount));
                emotion = emotion.concat(" friends");
            }
            else if(topCount == familyCount && !emotion.contains("family")) {
                imageViews.get(i).setImageResource(R.drawable.family);
                names.get(i).setText("Family");
                counts.get(i).setText(String.valueOf(topCount));
                emotion = emotion.concat(" family");
            }
            else if(topCount == noneCount && !emotion.contains("none")) {
                imageViews.get(i).setImageResource(R.drawable.none);
                names.get(i).setText("None");
                counts.get(i).setText(String.valueOf(topCount));
                emotion = emotion.concat(" none");
            }
        }
        StringViewModel viewModel = new ViewModelProvider(requireActivity()).get(StringViewModel.class);
        viewModel.setEmotion(emotion);
    }
}