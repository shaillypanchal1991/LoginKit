package com.sample.loginkit.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sample.loginkit.R;
import com.sample.loginkit.network.apiUtils.DataRepository;


public class Profile  {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("accountId")
    @Expose
    private String accountId;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("hasPin")
    @Expose
    private Boolean hasPin;
    @SerializedName("master")
    @Expose
    private Boolean master;
    @SerializedName("maturity")
    @Expose
    private String maturity;
    @SerializedName("preferences")
    @Expose
    private Preferences preferences;
    @SerializedName("lastModifiedDate")
    @Expose
    private Double lastModifiedDate;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;

    @SerializedName("scope")
    @Expose
    private String scope;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getHasPin() {
        return hasPin;
    }

    public void setHasPin(Boolean hasPin) {
        this.hasPin = hasPin;
    }

    public Boolean getMaster() {
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    public String getMaturity() {
        return maturity;
    }

    public void setMaturity(String maturity) {
        this.maturity = maturity;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public Double getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Double lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl).error(R.drawable.ic_plus).fallback(R.drawable.ic_plus).apply(DataRepository.getInstance().getrequestoptions())
                .into(view);
    }

}