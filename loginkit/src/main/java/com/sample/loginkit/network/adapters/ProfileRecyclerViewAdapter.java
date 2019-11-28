package com.sample.loginkit.network.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.sample.loginkit.R;
import com.sample.loginkit.databinding.FooterRowBinding;
import com.sample.loginkit.databinding.ProfileRowBinding;
import com.sample.loginkit.models.Profile;
import com.sample.loginkit.utils.LogUtils;

import java.util.List;

import static com.sample.loginkit.BR.model;


public class ProfileRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CustomClickListener {
    private static String TAG = ProfileRecyclerViewAdapter.class.getName();

    private List<Profile> dataModelList;
    private profileClickListener profileClickListener;


    public ProfileRecyclerViewAdapter(List<Profile> dataModelList, profileClickListener profileClickListener) {
        this.dataModelList = dataModelList;
        this.profileClickListener = profileClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ProfileRowBinding profileRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.profile_row, parent, false);

        FooterRowBinding footerRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.footer_row, parent, false);


        if (viewType == VIEW_TYPES.Footer) {
            return new FooterViewHolder(footerRowBinding);
        } else {
            return new ProfileViewHolder(profileRowBinding);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ProfileViewHolder) {
            Profile dataModel = dataModelList.get(position);
            ((ProfileViewHolder) holder).bind(dataModel);
            ((ProfileViewHolder) holder).profileRowBinding.setItemClickListener(this);

        } else if (holder instanceof FooterViewHolder) {
            Profile dataModel = new Profile();
            dataModel.setAvatarUrl("@drawable/ic_addprofile");
            dataModel.setNickname("Add profile");
            ((FooterViewHolder) holder).bind(dataModel);

        }
    }


    @Override
    public int getItemCount() {


        if (dataModelList == null) {
            return 0;
        }

        if (dataModelList.size() == 0) {

            return 1;
        }

        return dataModelList.size() + 1;
    }


    @Override
    public void cardClicked(Profile f) {
        this.profileClickListener.onProfileClicked(f);
        LogUtils.debug(TAG, "Profile Clicked " + f.getNickname());

    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        public ProfileRowBinding profileRowBinding;

        public ProfileViewHolder(ProfileRowBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.profileRowBinding = itemRowBinding;

        }

        public void bind(Object obj) {
            profileRowBinding.setVariable(model, obj);
            profileRowBinding.executePendingBindings();
        }
    }


    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterRowBinding footerRowBinding;

        public FooterViewHolder(FooterRowBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.footerRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            footerRowBinding.setVariable(model, obj);
            footerRowBinding.executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {


        if (position == dataModelList.size())
            return VIEW_TYPES.Footer;
        else
            return VIEW_TYPES.Normal;
    }


    private class VIEW_TYPES {

        public static final int Normal = 2;
        public static final int Footer = 3;
    }


    public interface profileClickListener {

        public void onProfileClicked(Profile profile);
    }
}
