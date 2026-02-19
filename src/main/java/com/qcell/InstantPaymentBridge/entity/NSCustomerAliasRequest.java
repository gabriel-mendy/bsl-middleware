package com.qcell.InstantPaymentBridge.entity;

import com.google.gson.annotations.SerializedName;

public class NSCustomerAliasRequest {

    @SerializedName("mobileNumber")
    String mobileNumber;

    public NSCustomerAliasRequest(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
}
