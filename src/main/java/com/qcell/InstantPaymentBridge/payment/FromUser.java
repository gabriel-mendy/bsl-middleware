package com.qcell.InstantPaymentBridge.payment;

import com.google.gson.annotations.SerializedName;

public class FromUser {
      @SerializedName("userIdentifier")
   String userIdentifier;


    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }
    public String getUserIdentifier() {
        return userIdentifier;
    }
    
}
