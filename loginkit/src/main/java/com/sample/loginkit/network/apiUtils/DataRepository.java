package com.sample.loginkit.network.apiUtils;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.sample.loginkit.init.RootLoginController;
import com.sample.loginkit.models.Login;
import com.sample.loginkit.models.Profile;
import com.sample.loginkit.network.RetrofitConfiguration;
import com.sample.loginkit.network.generic.DataWrapper;
import com.sample.loginkit.utils.LogUtils;
import com.sample.loginkit.utils.StringConstants;


import java.util.List;


import static androidx.constraintlayout.widget.Constraints.TAG;


/* Class defining major data storage functions like maintaining user session,credentials etc in the Encrypted Shared Preferences */

public class DataRepository {
    private String TAG = DataRepository.class.getName();

    private APIRequestInterface apiRequest;
    private List<Profile> _profileList;
    final MutableLiveData<Login> loginInfo = new MutableLiveData<>();
    final MutableLiveData<List<Profile>> profiles = new MutableLiveData<>();
    private static final DataRepository instance = new DataRepository();

    public DataRepository() {
        apiRequest = RetrofitConfiguration.getRetrofitInstance().create(APIRequestInterface.class);

    }

    public static DataRepository getInstance() {
        return instance;
    }

    public APIRequestInterface getApiRequest() {
        return apiRequest;
    }

    public void storeUserSessionDetails(Login userSessionObject) {

        Gson gson = new Gson();
        String userSessionObjectinString = gson.toJson((Login) userSessionObject);
        RootLoginController.getEncryptedPreferences()
                .edit()
                .putString(StringConstants.USER_OBJECT_KEY, userSessionObjectinString)
                .apply();

    }

    public Login fetchUserSessionDetails() {
        String decryptedUserSession = RootLoginController.getEncryptedPreferences().getString(StringConstants.USER_OBJECT_KEY, "");

        Login userSession = null;
        try {
            Gson gson = new Gson();
            userSession = gson.fromJson(decryptedUserSession, Login.class);
            LogUtils.debug(TAG, "Decrypted user Object" + userSession.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userSession;


    }

    public void clearUserSessionDetails() {


        RootLoginController.getEncryptedPreferences().edit()
                .putString(StringConstants.USER_OBJECT_KEY, null)
                .apply();

    }


    public void cacheProfileDetails(DataWrapper profileData) {


        _profileList = (List<Profile>) profileData.getData();


    }

    public List<Profile> get_profileList() {
        return _profileList;
    }

    public void storeUserName(String username) {
        RootLoginController.getEncryptedPreferences().edit()
                .putString(StringConstants.USERNAME_KEY, username)
                .apply();
    }

    public void storeRememberMeStatus(boolean rememberMe) {
        RootLoginController.getEncryptedPreferences().edit()
                .putBoolean(StringConstants.IS_REMEMBER_ME, rememberMe)

                .apply();
    }

    public void storePassword(String password) {
        RootLoginController.getEncryptedPreferences().edit()
                .putString(StringConstants.PASSWORD_KEY, password)

                .apply();
    }

    public String fetchUserName() {
        String decryptedValue = RootLoginController.getEncryptedPreferences().getString(StringConstants.USERNAME_KEY, "");
        return decryptedValue;

    }

    public boolean fetchRememberMe() {
        boolean decryptedValue = RootLoginController.getEncryptedPreferences().getBoolean(StringConstants.IS_REMEMBER_ME, false);
        return decryptedValue;

    }

    public String fetchPassword() {
        String decryptedValue = RootLoginController.getEncryptedPreferences().getString(StringConstants.PASSWORD_KEY, "");
        return decryptedValue;

    }

    public RequestOptions getrequestoptions() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon);

        return options;


    }

}
