package com.example.androidproject.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.androidproject.Model.DayDetailIcon;
import com.example.androidproject.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DayDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DayDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DayDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DayDetailFragment newInstance(String param1, String param2) {
        DayDetailFragment fragment = new DayDetailFragment();
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
        return inflater.inflate(R.layout.fragment_day_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<DayDetailIcon> icons = getIcons();

        RecyclerView recyclerView = view.findViewById(R.id.RecycleViewSubIcons);

        DayDetailIconAdapter adapter = new DayDetailIconAdapter(getContext(), icons);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));

        ImageView imageView = view.findViewById(R.id.imageViewPhoto);
        imageView.setImageResource(R.drawable.happy);
    }

    public ArrayList<DayDetailIcon> getIcons()
    {
        ImageView imageView = new ImageView(getContext());

        imageView.setImageResource(R.drawable.happy);

        ArrayList<DayDetailIcon> icons = new ArrayList<>();
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));
        icons.add(new DayDetailIcon(imageView));

        return icons;
    }
}