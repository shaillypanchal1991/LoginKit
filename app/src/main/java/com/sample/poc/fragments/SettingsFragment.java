package com.sample.poc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sample.loginkit.network.apiUtils.DataRepository;
import com.sample.poc.SplashActivity;
import com.sample.poc.R;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        TextView tv= view.findViewById(R.id.txtlogout);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataRepository.getInstance().clearUserSessionDetails();
                Intent intent= new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }


}
