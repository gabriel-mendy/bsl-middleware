package com.qcell.InstantPaymentBridge.payment;

import com.google.gson.annotations.SerializedName;

public class ToUser {
    
       @SerializedName("userIdentifier")
   String userIdentifier;

   @SerializedName("pouchId")
   String pouchId;


    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }
    public String getUserIdentifier() {
        return userIdentifier;
    }
    
    public void setPouchId(String pouchId) {
        this.pouchId = pouchId;
    }
    public String getPouchId() {
        return pouchId;
    }
}
