package com.sample.loginkit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Preferences{

    @SerializedName("uiLanguage")
    @Expose
    private String uiLanguage;
    @SerializedName("playback")
    @Expose
    private Playback playback;

    public String getUiLanguage() {
        return uiLanguage;
    }

    public void setUiLanguage(String uiLanguage) {
        this.uiLanguage = uiLanguage;
    }

    public Playback getPlayback() {
        return playback;
    }

    public void setPlayback(Playback playback) {
        this.playback = playback;
    }

}