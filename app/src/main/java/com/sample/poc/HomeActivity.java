package com.sample.poc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import com.sample.loginkit.analytics.AnalyticsServiceManager;
import com.sample.loginkit.init.RootLoginController;
import com.sample.loginkit.listeners.LoginKitEventListener;
import com.sample.loginkit.models.Login;
import com.sample.loginkit.models.Profile;
import com.sample.loginkit.network.error.CustomException;
import com.sample.loginkit.network.error.ValidationAPIException;
import com.sample.loginkit.utils.LogUtils;
import com.sample.poc.databinding.ActivityHomeBinding;
import com.sample.poc.fragments.AccountFragment;
import com.sample.poc.fragments.HomeFragment;
import com.sample.poc.fragments.SearchFragment;
import com.sample.poc.fragments.SettingsFragment;
import com.sample.poc.helper.BottomNavigationBehavior;

import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AnalyticsServiceManager.AnalyticsEventInterface, LoginKitEventListener.EventListenerExtended {

    ActivityHomeBinding binding;
    private String TAG = HomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        super.onCreate(savedInstanceState);


        initializeLibrary();

        binding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) binding.navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        loadFragment(new HomeFragment());

    }

    private void initializeLibrary() {

        RootLoginController rootLoginController = RootLoginController.init();
        rootLoginController.customConfiguration.isLoggingEnable = true;
        rootLoginController.customConfiguration.retryCount = 2;
        rootLoginController.customConfiguration.uiComponents.backgroundColor = "#ffffff";
        rootLoginController.customConfiguration.domainURL="https://usermgt-staging.shared-svc.bellmedia.ca";

        rootLoginController.loadLoginKit(this, rootLoginController);

        AnalyticsServiceManager.getInstance().setAnalyticsEventListener(this);
        RootLoginController.setEventListener(this);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_search:

                    fragment = new SearchFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_account:

                    fragment = new AccountFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_settings:

                    fragment = new SettingsFragment();
                    loadFragment(fragment);
                    return true;


            }

            return false;
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void pushEvent(String name, HashMap<String, String> eventProperties) {
        LogUtils.debug(TAG, "Analytics Event : " + name + " Params :" + eventProperties.toString());
    }

    @Override
    public void loginSuccessfulWith(Login loginResponse) {

        Log.e("Callback response","Login response"+loginResponse.getAccessToken());

    }

    @Override
    public void loginFailedWith(CustomException e) {

        Log.e("Callback response","Custom exception"+e.getErrorMessage(e.getErrorCode()));

    }

    @Override
    public void fetchProfilesSuccessfulWith(List<Profile> profileList) {

        Log.e("Callback response","Profile List fetched successdully response");

    }

    @Override
    public void fetchProfilesFailedWithError(CustomException e) {

        Log.e("Callback response","Profile List fetching error" );
    }

    @Override
    public void loginWithProfileIdSuccessfulWith(Login loginResponse) {

        Log.e("Callback response","Login with profile ID successful"+loginResponse.getAccessToken());

    }

    @Override
    public void loginWithProfileIdFailedWith(CustomException e) {

        Log.e("Callback response","Login with profile ID Failed");
    }

    @Override
    public void refreshTokenSuccessfulWith(Login loginResponse) {


        Log.e("Callback response","Token refreshed successfully ");

    }

    @Override
    public void refreshTokenFailedWith(CustomException e) {


        Log.e("Callback response","Token refresh failed ");

    }


    @Override
    public void logoutSuccessful() {

    }


    @Override
    public void optionalMethod() {

    }
}
