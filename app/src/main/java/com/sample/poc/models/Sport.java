package com.sample.poc.models;

public class Sport {

    String sportName;
    int imgURL;

    public Sport(String sportName, int imgURL) {
        this.sportName = sportName;
        this.imgURL = imgURL;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public int getImgURL() {
        return imgURL;
    }

    public void setImgURL(int imgURL) {
        this.imgURL = imgURL;
    }
}
