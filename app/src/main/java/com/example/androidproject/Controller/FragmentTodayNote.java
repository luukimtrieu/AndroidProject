package com.example.androidproject.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.androidproject.Model.Database.DayStatus;
import com.example.androidproject.Model.SharedViewModel;
import com.example.androidproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTodayNote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTodayNote extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText note;

    public FragmentTodayNote() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDayStatus4.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTodayNote newInstance(String param1, String param2) {
        FragmentTodayNote fragment = new FragmentTodayNote();
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
        return inflater.inflate(R.layout.fragment_todaynote, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getLiveDataDayStatus().observe(getViewLifecycleOwner(), new Observer<DayStatus>() {
            @Override
            public void onChanged(DayStatus dayStatus) {
                String note_text = dayStatus.note;
                note.setText(note_text);
            }
        });
    }

    public void initView(View view)
    {
        note = view.findViewById(R.id.editTextNote);
    }
}