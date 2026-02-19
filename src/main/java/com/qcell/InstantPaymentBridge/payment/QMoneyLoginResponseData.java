package com.qcell.InstantPaymentBridge.payment;

import com.google.gson.annotations.SerializedName;

public class QMoneyLoginResponseData {
    
   @SerializedName("access_token")
   String accessToken;

   @SerializedName("twoFactorEnable")
   String twoFactorEnable;

   @SerializedName("accessTokenExpiry")
   String accessTokenExpiry;


    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setTwoFactorEnable(String twoFactorEnable) {
        this.twoFactorEnable = twoFactorEnable;
    }
    public String getTwoFactorEnable() {
        return twoFactorEnable;
    }
    
    public void setAccessTokenExpiry(String accessTokenExpiry) {
        this.accessTokenExpiry = accessTokenExpiry;
    }
    public String getAccessTokenExpiry() {
        return accessTokenExpiry;
    }

}
