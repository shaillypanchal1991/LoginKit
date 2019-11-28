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
import com.sample.poc.models.Sport;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private ArrayList<Sport> _sportsList = new ArrayList<>();
    private RecyclerView _horizontalRecyclerView, _carouselRecyclerview;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(getActivity(), _sportsList);
        // _horizontalRecyclerView.setAdapter(new HorizontalAdapter(getActivity(), _sportsList));
        _horizontalRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        llm.setAutoMeasureEnabled(false);
        _horizontalRecyclerView.setLayoutManager(llm);

        _horizontalRecyclerView.getLayoutParams().height = (int) getHeightonepercent();


        // _horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        // HorizontalAdapter horizontalAdapter = new HorizontalAdapter(getActivity());
        _horizontalRecyclerView.setAdapter(horizontalAdapter);
        return view;


    }

    public float getwidthonepercent() {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float onepercent = (dpWidth * 45) / 100;
        return onepercent;
    }

    public float getHeightonepercent() {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float onepercent = (dpHeight * 45) / 100;
        return onepercent;
    }

    class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {
        private Context context;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public ImageView thumbnail;
            public LinearLayout llmain;

            public MyViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.txtName);
                thumbnail = view.findViewById(R.id.imgItem);
                llmain = view.findViewById(R.id.llmain);
            }
        }


        public HorizontalAdapter(Context context, ArrayList<Sport> sportsList) {
            this.context = context;


        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.horizontal_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final Sport sport = _sportsList.get(position);


            //  holder.llmain.getLayoutParams().height = (int)getHeightonepercent();

            holder.llmain.getLayoutParams().width = (int) getwidthonepercent();

            Glide.with(context)
                    .load(sport.getImgURL())

                    .into(holder.thumbnail);
            // holder.name.setText(sport.getSportName().toString());
        }

        @Override
        public int getItemCount() {
            return _sportsList.size();
        }
    }


}

