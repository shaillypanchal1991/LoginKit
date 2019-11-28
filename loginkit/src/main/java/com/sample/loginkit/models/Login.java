package com.sample.loginkit.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Login implements Serializable {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("localization")
    @Expose
    private String localization;
    @SerializedName("account_id")
    @Expose
    private String accountId;
    @SerializedName("creation_date")
    @Expose
    private Long creationDate;
    @SerializedName("ais_id")
    @Expose
    private String aisId;
    @SerializedName("profile_id")
    @Expose
    private Object profileId;
    @SerializedName("jti")
    @Expose
    private String jti;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public String getLocalization() {
        return localization;
    }

    public String getAccountId() {
        return accountId;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public String getAisId() {
        return aisId;
    }

    public Object getProfileId() {
        return profileId;
    }

    public String getJti() {
        return jti;
    }


    @Override
    public String toString() {
        return "Login{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", scope='" + scope + '\'' +
                ", localization='" + localization + '\'' +
                ", accountId='" + accountId + '\'' +
                ", creationDate=" + creationDate +
                ", aisId='" + aisId + '\'' +
                ", profileId=" + profileId +
                ", jti='" + jti + '\'' +
                '}';
    }



}