package com.sample.loginkit.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.sample.loginkit.R;
import com.sample.loginkit.analytics.AnalyticsServiceManager;
import com.sample.loginkit.databinding.ActivityLoginViewBinding;
import com.sample.loginkit.init.RootLoginController;
import com.sample.loginkit.models.Login;
import com.sample.loginkit.network.apiUtils.DataRepository;
import com.sample.loginkit.network.error.CustomException;
import com.sample.loginkit.network.generic.APIObserver;
import com.sample.loginkit.utils.LogUtils;
import com.sample.loginkit.viewmodel.LoginViewModel;

import java.util.HashMap;
import java.util.Map;

public class LoginView extends AppCompatActivity implements View.OnClickListener {
    ActivityLoginViewBinding binding;
    LoginViewModel loginViewModel;
    private String userName;
    private boolean isRemembered;

    private String TAG = LoginView.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(LoginView.this, R.layout.activity_login_view);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);


        binding.btnSignIn.setEnabled(false);
        binding.btnSignIn.setOnClickListener(this);

        //apply config

        binding.rootLayout.setBackgroundColor(Color.parseColor(RootLoginController.customConfiguration.uiComponents.backgroundColor));



        binding.editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidEmail(s.toString())) {
                    binding.txtinputEmail.setError("Enter a valid address");
                    binding.btnSignIn.setEnabled(false);
                } else if (binding.editTextPassword.getText().toString().equals("")) {
                    binding.btnSignIn.setEnabled(false);
                    binding.txtinputEmail.setError(null);
                } else {
                    binding.txtinputEmail.setError(null);

                }

            }
        });


        binding.editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.btnSignIn.setEnabled(false);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isPasswordLengthGreaterThan5(s.toString())) {
                    binding.btnSignIn.setEnabled(false);
                    binding.txtinputPassword.setError("Password is too short");
                } else if (isPasswordLengthGreaterThan120(s.toString())) {
                    binding.btnSignIn.setEnabled(false);
                    binding.txtinputPassword.setError("Password is too long");
                } else if (!isPasswordStrong(s.toString())) {
                    binding.btnSignIn.setEnabled(false);
                    binding.txtinputPassword.setError("Password is too weak");
                } else if (binding.txtinputEmail.getError() != null) {
                    binding.btnSignIn.setEnabled(false);
                    binding.txtinputPassword.setError(null);
                } else {

                    binding.txtinputPassword.setError(null);
                }
            }
        });

        userName = DataRepository.getInstance().fetchUserName();
        isRemembered = DataRepository.getInstance().fetchRememberMe();
        if (isRemembered) {
            binding.editTextEmail.setText(userName);
        }
        binding.checkBoxRememberMe.setChecked(isRemembered);

        //binding.checkBoxRememberMe.setChecked(boolean(rememberedUserCredentialsSplitted[2]));


        loginViewModel.getLivedata().observe(this, new APIObserver<Login>(new APIObserver.ChangeListener<Login>() {
            @Override
            public void onSuccess(Login dataWrapper) {


                HashMap<String,String> eventParams = new HashMap<String,String>();
                eventParams.put("username", binding.editTextEmail.getText().toString());
                eventParams.put("isRememberMe",String.valueOf(binding.checkBoxRememberMe.isChecked()));
                eventParams.put("isProfile","false");
                eventParams.put("responseObject",dataWrapper.toString());
                AnalyticsServiceManager.getInstance().pushAnalyticsEvent("login_success",eventParams);


                Intent intent = new Intent(LoginView.this, ProfileView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();

            }

            @Override
            public void onException(CustomException exception) {


                HashMap<String,String> eventParams = new HashMap<String,String>();
                eventParams.put("username", binding.editTextEmail.getText().toString());
                eventParams.put("isRememberMe",String.valueOf(binding.checkBoxRememberMe.isChecked()));
                eventParams.put("isProfile","false");
                eventParams.put("errorResponse",exception.getErrorCode()+ ": " +exception.getErrorMessage(exception.getErrorCode()));
                AnalyticsServiceManager.getInstance().pushAnalyticsEvent("login_failure",eventParams);
                Snackbar snackbar = Snackbar
                        .make(binding.rootLayout, exception.getErrorMessage(exception.getErrorCode()), Snackbar.LENGTH_LONG);
                snackbar.show();


            }
        }));

    }

    public final boolean isValidEmail(String target) {
        if (target == null || target.equals("")) {
            binding.btnSignIn.setEnabled(false);
            return false;
        } else {
            binding.btnSignIn.setEnabled(true);
            //android Regex to check the email address Validation
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();

        }
    }


    public boolean isPasswordLengthGreaterThan5(String target) {
        if (target == null || target.equals("")) {
            binding.btnSignIn.setEnabled(false);
            return false;
        } else {

            return target.length() > 5;
        }

    }

    public boolean isPasswordLengthGreaterThan120(String target) {
        if (target == null || target.equals("")) {
            binding.btnSignIn.setEnabled(false);
            return false;
        } else {
            binding.btnSignIn.setEnabled(true);
            return target.length() > 120;
        }
    }

    public boolean isPasswordStrong(String target) {
        if (target == null || target.equals("")) {
            binding.btnSignIn.setEnabled(false);
            return false;
        } else {
            binding.btnSignIn.setEnabled(true);
            if (target.equalsIgnoreCase("password") || target.equalsIgnoreCase("qwerty")) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (isNetworkConnected()) {
            if (binding.txtinputEmail.getError() == null && binding.txtinputPassword.getError() == null) {

                loginViewModel.loginWithCredentials(binding.editTextEmail.getText().toString(), binding.editTextPassword.getText().toString(), binding.checkBoxRememberMe.isChecked());

                HashMap<String,String> eventParams = new HashMap<String,String>();
                eventParams.put("username", binding.editTextEmail.getText().toString());
                eventParams.put("isRememberMe",String.valueOf(binding.checkBoxRememberMe.isChecked()));
                AnalyticsServiceManager.getInstance().pushAnalyticsEvent("login_attempted",eventParams);



                LogUtils.debug(TAG,"Login Attempted : Username : "+ binding.editTextEmail.getText().toString());


            } else {
                Snackbar snackbar = Snackbar
                        .make(binding.rootLayout, "Please enter valid credentials", Snackbar.LENGTH_LONG);
                snackbar.show();
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
        //super.onBackPressed();
    }
}
