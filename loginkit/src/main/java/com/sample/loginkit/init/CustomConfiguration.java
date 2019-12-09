package com.sample.loginkit.init;

import com.google.android.material.resources.TextAppearance;
import com.sample.loginkit.utils.StringConstants;

public class CustomConfiguration {

   //Configuration parameters for the client app to change
    public String domainURL = "";
    public int retryCount = 3;
    public boolean isLoggingEnable = true;
    public UIComponents uiComponents = new UIComponents();
    public HTTPErrorMapping httpErrorMapping = new HTTPErrorMapping();

    //UI Configuration parameters for the client app to change
    public class UIComponents {

        public double buttonCornerRadius = 0.0;
        public String buttonBackGroundColorHex = "#ffffff";
        public String borderColor = "#9999999";
        public String buttonTextColor = "#ffffff";
        public String backgroundColor = "#ffffff";
        public String textFieldColor = "#27292b";
        public String placeHolderColorHex = "#060606";
        public String textFieldUnderLineColor = "#27292b";
        public String abelTextColor = "#27292b";



    }

    //HTTP Error Message configuration parameters for the client app to change

    public class HTTPErrorMapping {

        public String Error_400 = "Bad Request";
        public String Error_401 = StringConstants.Error_401;
        public String Error_404 = StringConstants.Error_404;
        public String Error_500 = StringConstants.Error_500;
        public String Error_503 = StringConstants.Error_503;
        public String Error_504 = StringConstants.Error_504;
        public String Error_Unknown=StringConstants.Error_Unknown;


    }


}
