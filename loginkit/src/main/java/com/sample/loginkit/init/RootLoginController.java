package com.sample.loginkit.init;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.sample.loginkit.listeners.LoginKitEventListener;
import com.sample.loginkit.network.RetrofitConfiguration;
import com.sample.loginkit.network.apiUtils.APIRequestInterface;
import com.sample.loginkit.utils.LogUtils;
import com.sample.loginkit.view.LoginView;
import com.sample.loginkit.view.ProfileView;

import java.io.IOException;
import java.security.GeneralSecurityException;


import retrofit2.http.PUT;

import static com.sample.loginkit.utils.StringConstants.ENCRYPTED_PREFERENCES_NAME;
import static com.sample.loginkit.utils.StringConstants.USER_OBJECT_KEY;

public class RootLoginController {

    private String TAG = RootLoginController.class.getName();

    private static volatile RootLoginController instance = new RootLoginController();
    public static CustomConfiguration customConfiguration = new CustomConfiguration();
    private Context _context;
    private static SharedPreferences encryptedPreferences = null;

    private static LoginKitEventListener.EventListenerExtended eventListener;

    public RootLoginController() {
        if (instance != null) {
            throw new RuntimeException(
                    "Use getInstance() method to get the single instance of this class.");
        }
    }


    public static RootLoginController init() {
        SharedPreferences encryptedPreferences = null;
        if (instance == null) {

            synchronized (RootLoginController.class) {

                if (instance == null) {

                    instance = new RootLoginController();


                }
            }

        }

        return instance;
    }

    public static void setEventListener(LoginKitEventListener.EventListenerExtended eventListenerExtended) {

        eventListener = eventListenerExtended;

    }

    public static LoginKitEventListener.EventListenerExtended getEventListener() {
        return eventListener;
    }

    public void loadLoginKit(Context context, RootLoginController rootLoginController) {
        _context = context;
        //apply custom configuration
        customConfiguration = rootLoginController.customConfiguration;


        //initialize Encrypted Preferences
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);


            encryptedPreferences = EncryptedSharedPreferences.create(
                    ENCRYPTED_PREFERENCES_NAME,
                    masterKeyAlias,
                    context.getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            LogUtils.debug(TAG, "Initializing Encrypted Preferences..");

        } catch (GeneralSecurityException e) {
            LogUtils.debug(TAG, "Security Exception : " + e.getLocalizedMessage());

            e.printStackTrace();
        } catch (IOException e) {
            LogUtils.debug(TAG, "Security Exception : " + e.getLocalizedMessage());
            e.printStackTrace();
        }


        //check for Active User Session

        if (encryptedPreferences.getString(USER_OBJECT_KEY, "") == "") {
            LogUtils.debug(TAG, "No User Session Found. Navigating to Login Activity");
            Intent intent = new Intent(_context, LoginView.class);
            _context.startActivity(intent);


        } else {
            LogUtils.debug(TAG, "User Session Found. Navigating to Profiles Activity");
            Intent intent = new Intent(_context, ProfileView.class);
            _context.startActivity(intent);

        }


    }

    public static CustomConfiguration getCustomConfiguration() {
        return customConfiguration;
    }

    public static SharedPreferences getEncryptedPreferences() {
        return encryptedPreferences;
    }
}

