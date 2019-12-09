package com.sample.poc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.sample.poc.R;
import com.sample.poc.adapters.HorizontalAdapter;
import com.sample.poc.adapters.MainVerticalAdapter;
import com.sample.poc.models.Sport;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private ArrayList<Sport> _sportsList = new ArrayList<>();
    private RecyclerView _horizontalRecyclerView, _mainRecyclerview;
    private float dpHeight,dpWidth;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    /*Adding dummy values for horizontal list in homepage */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*Getting width and height of the device */
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        dpWidth = displayMetrics.widthPixels / displayMetrics.density;


        /*Adding dummy data */

        _sportsList.add(new Sport("NBA", R.drawable.nba));
        _sportsList.add(new Sport("Tennis", R.drawable.tennis));
        _sportsList.add(new Sport("Football", R.drawable.football));
        _sportsList.add(new Sport("Rugby", R.drawable.rugby));
        _sportsList.add(new Sport("UFC", R.drawable.ufc));
        _sportsList.add(new Sport("Badminton", R.drawable.nba));
        _sportsList.add(new Sport("Table Tennis", R.drawable.tennis));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.homeview_fragment, container, false);

        _horizontalRecyclerView = view.findViewById(R.id.horizontalRecyclerView);
        _mainRecyclerview = view.findViewById(R.id.mainRecyclerView);

        /*for horizontal
        recycler view */

        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(getActivity(), _sportsList,getWidthByPercent(45));
        _horizontalRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        llm.setAutoMeasureEnabled(false);
        _horizontalRecyclerView.setLayoutManager(llm);

        _horizontalRecyclerView.getLayoutParams().height = (int) setRecyclerviewHeightByPercent(45);


        _horizontalRecyclerView.setAdapter(horizontalAdapter);


        /* for vertical recyclerview */
        MainVerticalAdapter mainVerticalAdapter = new MainVerticalAdapter( getHeightForRecyclerView(70));
        _mainRecyclerview.setHasFixedSize(true);
        LinearLayoutManager llm1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        llm.setAutoMeasureEnabled(false);
        _mainRecyclerview.setLayoutManager(llm1);



        _mainRecyclerview.setAdapter(mainVerticalAdapter);
        return view;


    }

    public float getWidthByPercent(int percent) {

        float onepercent = (dpWidth * percent) / 100;
        return onepercent;
    }

    public float setRecyclerviewHeightByPercent(int percent) {

        float onepercent = (dpHeight * percent) / 100;
        return onepercent;
    }


    public float getHeightForRecyclerView(int percent) {

        float onepercent = (dpHeight * 70) / 100;
        return onepercent;
    }




}



