package com.sample.loginkit.network.error;

import com.sample.loginkit.init.CustomConfiguration;
import com.sample.loginkit.init.RootLoginController;

public class CustomException extends Exception {
    private final int errorCode;


    public CustomException(String message, int code) {
        super(message);
        this.errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage(int errorCode){
        switch (errorCode){
            case 401 : return RootLoginController.customConfiguration.httpErrorMapping.Error_400;
            case 500 : return "The server seems to be down";
            case 400 : return "Bad credentials";

        }
        return "Something went wrong.Please try again later";
    }

}
