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
 * Use the {@link FragmentSocial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSocial extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button friend;
    private Button family;
    private Button GFBF;
    private Button acquaintance;
    private Button none;

    public FragmentSocial() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDayStatus3.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSocial newInstance(String param1, String param2) {
        FragmentSocial fragment = new FragmentSocial();
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
        return inflater.inflate(R.layout.fragment_social, container, false);
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
                boolean friend_bool = dayStatus.friends;
                boolean GFBF_bool = dayStatus.GFBF;
                boolean family_bool = dayStatus.family;
                boolean acquaintance_bool = dayStatus.acquaintance;
                boolean none_bool = dayStatus.none;
                if(friend_bool)
                    friend.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.friend));
                if(GFBF_bool)
                    GFBF.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.love));
                if(family_bool)
                    family.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.family));
                if(acquaintance_bool)
                    acquaintance.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.acquaintance));
                if(none_bool)
                    none.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.none));

            }
        });

        ;
    }

    public void initView(View view)
    {
        friend = view.findViewById(R.id.buttonFriends);
        family = view.findViewById(R.id.buttonFamily);
        GFBF = view.findViewById(R.id.buttonGFBF);
        acquaintance = view.findViewById(R.id.buttonAcquaintance);
        none = view.findViewById(R.id.buttonNone);
    }

    public void setEvent()
    {
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(friend.getBackground().getConstantState()
                        == ResourcesCompat.getDrawable(getResources(), R.drawable.friend_modified, null).getConstantState())
                    friend.setBackgroundResource(R.drawable.friend);
                else
                    friend.setBackgroundResource(R.drawable.friend_modified);
            }
        });

        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(family.getBackground().getConstantState()
                        == ResourcesCompat.getDrawable(getResources(), R.drawable.family_modified, null).getConstantState())
                    family.setBackgroundResource(R.drawable.family);
                else
                    family.setBackgroundResource(R.drawable.family_modified);
            }
        });

        GFBF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GFBF.getBackground().getConstantState()
                        == ResourcesCompat.getDrawable(getResources(), R.drawable.love_modified, null).getConstantState())
                    GFBF.setBackgroundResource(R.drawable.love);
                else
                    GFBF.setBackgroundResource(R.drawable.love_modified);
            }
        });

        acquaintance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(acquaintance.getBackground().getConstantState()
                        == ResourcesCompat.getDrawable(getResources(), R.drawable.acquaintance_modifie, null).getConstantState())
                    acquaintance.setBackgroundResource(R.drawable.acquaintance);
                else
                    acquaintance.setBackgroundResource(R.drawable.acquaintance_modifie);
            }
        });

        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(none.getBackground().getConstantState()
                        == ResourcesCompat.getDrawable(getResources(), R.drawable.none_modified, null).getConstantState())
                    none.setBackgroundResource(R.drawable.none);
                else
                    none.setBackgroundResource(R.drawable.none_modified);
            }
        });
    }
}