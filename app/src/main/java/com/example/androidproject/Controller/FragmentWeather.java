package com.example.androidproject.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidproject.Model.Database.DayStatus;
import com.example.androidproject.Model.SharedViewModel;
import com.example.androidproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWeather#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWeather extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button sunny;
    private Button cloudy;
    private Button rainy;
    private Button windy;
    private Button snowy;

    public Button getSunny() {
        return sunny;
    }

    public Button getCloudy() {
        return cloudy;
    }

    public Button getRainy() {
        return rainy;
    }

    public Button getWindy() {
        return windy;
    }

    public Button getSnowy() {
        return snowy;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentWeather() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDayStatus2.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWeather newInstance(String param1, String param2) {
        FragmentWeather fragment = new FragmentWeather();
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
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setEvent();
        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getLiveDataDayStatus().observe(getViewLifecycleOwner(), new Observer<DayStatus>() {
            @Override
            public void onChanged(DayStatus dayStatus) {
                boolean sunny_bool = dayStatus.sunny;
                boolean rainy_bool = dayStatus.rainy;
                boolean cloudy_bool = dayStatus.cloudy;
                boolean windy_bool = dayStatus.windy;
                boolean snowy_bool = dayStatus.snowy;
                if(sunny_bool)
                    sunny.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.sunny));
                if(rainy_bool)
                    rainy.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.rainy));
                if(cloudy_bool)
                    cloudy.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.cloudy));
                if(windy_bool)
                    windy.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.wind));
                if(snowy_bool)
                    snowy.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.snowy));

            }
        });
    }

    public void initView(View view)
    {
        sunny = view.findViewById(R.id.buttonSunny);
        rainy = view.findViewById(R.id.buttonRainy);
        windy = view.findViewById(R.id.buttonWindy);
        snowy = view.findViewById(R.id.buttonSnowy);
        cloudy = view.findViewById(R.id.buttonCloudy);
    }

    public void setEvent()
    {
        sunny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sunny.getBackground().getConstantState()
                        == ResourcesCompat.getDrawable(getResources(), R.drawable.sunny_modified, null).getConstantState())
                    sunny.setBackgroundResource(R.drawable.sunny);
                else
                    sunny.setBackgroundResource(R.drawable.sunny_modified);
            }
        });

        rainy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rainy.getBackground().getConstantState()
                        == ResourcesCompat.getDrawable(getResources(), R.drawable.rainy_modified, null).getConstantState())
                    rainy.setBackgroundResource(R.drawable.rainy);
                else
                    rainy.setBackgroundResource(R.drawable.rainy_modified);
            }
        });

        cloudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cloudy.getBackground().getConstantState()
                        == ResourcesCompat.getDrawable(getResources(), R.drawable.cloudy_modified, null).getConstantState())
                    cloudy.setBackgroundResource(R.drawable.cloudy);
                else
                    cloudy.setBackgroundResource(R.drawable.cloudy_modified);
            }
        });

        windy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(windy.getBackground().getConstantState()
                        == ResourcesCompat.getDrawable(getResources(), R.drawable.wind_modified, null).getConstantState())
                    windy.setBackgroundResource(R.drawable.wind);
                else
                    windy.setBackgroundResource(R.drawable.wind_modified);
            }
        });

        snowy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(snowy.getBackground().getConstantState()
                        == ResourcesCompat.getDrawable(getResources(), R.drawable.snowy_modified, null).getConstantState())
                    snowy.setBackgroundResource(R.drawable.snowy);
                else
                    snowy.setBackgroundResource(R.drawable.snowy_modified);
            }
        });
    }
}