package com.example.androidproject.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HolderReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HolderReportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final int NUM_PAGES = 2;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter fragmentStateAdapter;

    public HolderReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HolderReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HolderReportFragment newInstance(String param1, String param2) {
        HolderReportFragment fragment = new HolderReportFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_holder_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.viewPager2);
        fragmentStateAdapter = new ScreenSlidePageAdapter(this);
        viewPager2.setAdapter(fragmentStateAdapter);
    }

    private class ScreenSlidePageAdapter extends FragmentStateAdapter {
        public ScreenSlidePageAdapter(Fragment fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if(position == 0)
                return new IconRankingFragment();
            else
                return new IconRankingSecondFragment();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}
