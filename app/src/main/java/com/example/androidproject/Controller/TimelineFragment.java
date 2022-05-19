package com.example.androidproject.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.Model.DayDetailIcon;
import com.example.androidproject.Model.TimelineItem;
import com.example.androidproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BottomNavigationView bottomNavigationView;

    public TimelineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimelineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimelineFragment newInstance(String param1, String param2) {
        TimelineFragment fragment = new TimelineFragment();
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
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<TimelineItem> items = getItem();
        RecyclerView recyclerView = view.findViewById(R.id.recycleViewTimeline);
        TimelineAdapter adapter = new TimelineAdapter(getContext(), items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.timelineFragment );
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.calendarFragment:
                        Navigation.findNavController(view).navigate(R.id.action_global_calendarFragment);
                        break;
                    case R.id.timelineFragment:
                        Navigation.findNavController(view).navigate(R.id.action_global_timelineFragment);
                        break;
                    case R.id.reportFragment:
                        Navigation.findNavController(view).navigate(R.id.action_global_reportFragment);
                        break;
                    case R.id.settingFragment:
                        Navigation.findNavController(view).navigate(R.id.action_global_settingFragment);
                        break;
                }
                return true;
            }
        });
    }

    public ArrayList<TimelineItem> getItem()
    {
        ImageView mainIcon = new ImageView(getContext());
        mainIcon.setImageResource(R.drawable.happy);

        TextView dayMonth = new TextView(getContext());
        dayMonth.setText("haha");

        TextView straightLine = new TextView(getContext());
        straightLine.setText("straight");

        ArrayList<DayDetailIcon> icons = getIcons();

        DayDetailIconAdapter adapter = new DayDetailIconAdapter(getContext(), icons);

        RecyclerView subIcons = new RecyclerView(getContext());

        subIcons.setAdapter(adapter);

        subIcons.setLayoutManager(new GridLayoutManager(getContext(), 5));

        TextView notes = new TextView(getContext());
        notes.setText("notes ne");

        ImageView photo = new ImageView(getContext());
        photo.setImageResource(R.drawable.happy);

        TimelineItem item = new TimelineItem(mainIcon, dayMonth, straightLine, subIcons, notes, photo);

        ArrayList<TimelineItem> items = new ArrayList<>();
        items.add(item);
        items.add(item);
        items.add(item);
        return items;
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