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
import com.example.androidproject.Model.StringViewModel;
import com.example.androidproject.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondIconRankingTopTenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondIconRankingTopTenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView imageViewTop6;
    private ImageView imageViewTop7;
    private ImageView imageViewTop8;
    private ImageView imageViewTop9;
    private ImageView imageViewTop10;
    List<ImageView> imageViews;
    private TextView tvTop6Name;
    private TextView tvTop7Name;
    private TextView tvTop8Name;
    private TextView tvTop9Name;
    private TextView tvTop10Name;
    List<TextView> names;
    private TextView tvTop6Count;
    private TextView tvTop7Count;
    private TextView tvTop8Count;
    private TextView tvTop9Count;
    private TextView tvTop10Count;
    List<TextView> counts;
    private String year;
    private AppDatabase db;
    private String emotion;

    public SecondIconRankingTopTenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondIconRankingTopTenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondIconRankingTopTenFragment newInstance(String param1, String param2) {
        SecondIconRankingTopTenFragment fragment = new SecondIconRankingTopTenFragment();
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
        return inflater.inflate(R.layout.fragment_second_icon_ranking_top_ten, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StringViewModel viewModel = new ViewModelProvider(requireActivity()).get(StringViewModel.class);
        viewModel.getLiveData2().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                emotion = s;
            }
        });

        initView(view);

        viewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                year = s.substring(0,4);
                db = Room.databaseBuilder(getContext(), AppDatabase.class, "database.db").allowMainThreadQueries().build();
                setTopTen();
            }
        });
    }

    public void initView(View view)
    {
        imageViews = new ArrayList<>();
        names = new ArrayList<>();
        counts = new ArrayList<>();

        imageViewTop6 = view.findViewById(R.id.second_imageViewTop6);
        imageViewTop7 = view.findViewById(R.id.second_imageViewTop7);
        imageViewTop8 = view.findViewById(R.id.second_imageViewTop8);
        imageViewTop9 = view.findViewById(R.id.second_imageViewTop9);
        imageViewTop10 = view.findViewById(R.id.second_imageViewTop10);
        imageViews.add(imageViewTop6);
        imageViews.add(imageViewTop7);
        imageViews.add(imageViewTop8);
        imageViews.add(imageViewTop9);
        imageViews.add(imageViewTop10);

        tvTop6Name = view.findViewById(R.id.second_textViewTop6_IconName);
        tvTop7Name = view.findViewById(R.id.second_textViewTop7_IconName);
        tvTop8Name = view.findViewById(R.id.second_textViewTop8_IconName);
        tvTop9Name = view.findViewById(R.id.second_textViewTop9_IconName);
        tvTop10Name = view.findViewById(R.id.second_textViewTop10_IconName);
        names.add(tvTop6Name);
        names.add(tvTop7Name);
        names.add(tvTop8Name);
        names.add(tvTop9Name);
        names.add(tvTop10Name);

        tvTop6Count = view.findViewById(R.id.second_textViewTop6Count);
        tvTop7Count = view.findViewById(R.id.second_textViewTop7Count);
        tvTop8Count = view.findViewById(R.id.second_textViewTop8Count);
        tvTop9Count = view.findViewById(R.id.second_textViewTop9Count);
        tvTop10Count = view.findViewById(R.id.second_textViewTop10Count);
        counts.add(tvTop6Count);
        counts.add(tvTop7Count);
        counts.add(tvTop8Count);
        counts.add(tvTop9Count);
        counts.add(tvTop10Count);
    }

    public void setTopTen()
    {
        List<Integer> integers = new ArrayList<>();

        int rainyCount = db.dayStatusDao().getRainyCount(year);
        integers.add(rainyCount);

        int sunnyCount = db.dayStatusDao().getSunnyCount(year);
        integers.add(sunnyCount);

        int windyCount = db.dayStatusDao().getWindyCount(year);
        integers.add(windyCount);

        int snowyCount = db.dayStatusDao().getSnowyCount(year);
        integers.add(snowyCount);

        int cloudyCount = db.dayStatusDao().getCloudyCount(year);
        integers.add(cloudyCount);

        int friendsCount = db.dayStatusDao().getFriendsCount(year);
        integers.add(friendsCount);

        int familyCount = db.dayStatusDao().getFamilyCount(year);
        integers.add(familyCount);

        int GFBFcount = db.dayStatusDao().getGFBFCount(year);
        integers.add(GFBFcount);

        int acquaintanceCount = db.dayStatusDao().getAcquaintanceCount(year);
        integers.add(acquaintanceCount);

        int noneCount = db.dayStatusDao().getNoneCount(year);
        integers.add(noneCount);
        integers.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                return t1.compareTo(integer);
            }
        });

        String emotion = this.emotion;
        for(int i = 0; i <= 4; i++)
        {
            int topCount = integers.get(i + 5);
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

    }
}