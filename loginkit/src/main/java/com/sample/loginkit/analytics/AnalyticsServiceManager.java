package com.sample.loginkit.analytics;

import java.util.HashMap;

public class AnalyticsServiceManager {
    private static final AnalyticsServiceManager ourInstance = new AnalyticsServiceManager();
    AnalyticsEventInterface analyticsEventListener;

    public static AnalyticsServiceManager getInstance() {
        return ourInstance;
    }

    public void setAnalyticsEventListener(AnalyticsServiceManager.AnalyticsEventInterface listener) {

        this.analyticsEventListener = listener;
    }


    private AnalyticsServiceManager() {
    }

    public void pushAnalyticsEvent(String eventName, HashMap<String, String> eventProperties) {
        if (analyticsEventListener != null) {
            analyticsEventListener.pushEvent(eventName, eventProperties);
        }
    }

    public interface AnalyticsEventInterface {

        public void pushEvent(String name, HashMap<String, String> eventProperties);


    }
}
