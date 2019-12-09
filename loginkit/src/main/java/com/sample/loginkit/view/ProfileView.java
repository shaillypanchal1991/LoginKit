package com.sample.loginkit.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.sample.loginkit.R;
import com.sample.loginkit.databinding.ActivityProfileViewBinding;
import com.sample.loginkit.init.RootLoginController;
import com.sample.loginkit.models.Login;
import com.sample.loginkit.models.Profile;
import com.sample.loginkit.network.adapters.ProfileRecyclerViewAdapter;
import com.sample.loginkit.network.error.CustomException;
import com.sample.loginkit.network.generic.APIObserver;
import com.sample.loginkit.utils.AutoFitGridLayoutManager;
import com.sample.loginkit.utils.GridItemSpacingDecorator;
import com.sample.loginkit.utils.LogUtils;
import com.sample.loginkit.viewmodel.ProfileLoginViewModel;
import com.sample.loginkit.viewmodel.ProfileViewModel;

import java.util.List;

public class ProfileView extends AppCompatActivity implements ProfileRecyclerViewAdapter.profileClickListener {
    ProfileViewModel profileViewModel;
    ActivityProfileViewBinding binding;
    ProfileLoginViewModel profileLoginViewModel;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_profile_view);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileLoginViewModel = ViewModelProviders.of(this).get(ProfileLoginViewModel.class);

        binding = DataBindingUtil.setContentView(ProfileView.this, R.layout.activity_profile_view);
        binding.setLifecycleOwner(this);
        binding.setProfileViewModel(profileViewModel);
        profileViewModel.getProfilesOfUser();

        binding.recyclerView.setLayoutManager(new AutoFitGridLayoutManager(this, 200));

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        binding.recyclerView.addItemDecoration(new GridItemSpacingDecorator(2, dpToPx(10), true));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());

        profileViewModel.getLivedata().observe(this, new APIObserver<List<Profile>>(new APIObserver.ChangeListener<List<Profile>>() {


            @Override
            public void onSuccess(List<Profile> dataWrapper) {
                List<Profile> profiles = dataWrapper;
                LogUtils.debug(TAG, "profiles " + dataWrapper.size());
                RootLoginController.getEventListener().fetchProfilesSuccessfulWith(dataWrapper);
                populateData(profiles);


            }

            @Override
            public void onException(CustomException exception) {
                Snackbar snackbar = Snackbar
                        .make(binding.rootLayout, exception.getErrorMessage(exception.getErrorCode()), Snackbar.LENGTH_LONG);
                snackbar.show();
                RootLoginController.getEventListener().fetchProfilesFailedWithError(exception);
                LogUtils.debug(TAG, "profiles " + exception.getMessage());
            }
        }));


        profileLoginViewModel.getLivedata().observe(this, new APIObserver<Login>(new APIObserver.ChangeListener<Login>() {
            @Override
            public void onSuccess(Login dataWrapper) {

                RootLoginController.getEventListener().loginWithProfileIdSuccessfulWith(dataWrapper);

                finish();

            }

            @Override
            public void onException(CustomException exception) {

                Snackbar snackbar = Snackbar
                        .make(binding.rootLayout, exception.getErrorMessage(exception.getErrorCode()), Snackbar.LENGTH_LONG);
                snackbar.show();
                RootLoginController.getEventListener().loginWithProfileIdFailedWith(exception);

                //  Log.e(TAG, "" + exception.getMessage().toString());
            }
        }));


    }

    private void populateData(List<Profile> profiles) {


        ProfileRecyclerViewAdapter profileRecyclerViewAdapter = new ProfileRecyclerViewAdapter(profiles, ProfileView.this);

        binding.setProfileViewAdapter(profileRecyclerViewAdapter);


    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));

    }

    @Override
    public void onProfileClicked(final Profile profile) {
        if (isNetworkConnected()) {

            if (profile.getHasPin()) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Enter PIN");

                final EditText input = new EditText(this);

                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        profileLoginViewModel.loginwithProfileID(profile, Integer.valueOf(input.getText().toString()));

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            } else {
                profileLoginViewModel.loginwithProfileID(profile, 0);
            }
        } else {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
    }
}
