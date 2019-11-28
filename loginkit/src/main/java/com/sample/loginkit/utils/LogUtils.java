package com.sample.loginkit.utils;

import android.util.Log;

import com.sample.loginkit.init.RootLoginController;

public class LogUtils {

    public static void debug(final String tag, String message) {
        if (RootLoginController.getCustomConfiguration().isLoggingEnable) {
            Log.d(tag, message);
        }
    }
}
