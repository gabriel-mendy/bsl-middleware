package com.qcell.InstantPaymentBridge.payment;

import com.google.gson.annotations.SerializedName;

public class QMoneyPaymentResponse {
    
    @SerializedName("data")
    QMoneyPaymentResponseData data;

    @SerializedName("responseCode")
    String responseCode;

    @SerializedName("responseMessage")
    String responseMessage;


    public void setData(QMoneyPaymentResponseData data) {
        this.data = data;
    }
    public QMoneyPaymentResponseData getData() {
        return data;
    }
    
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
    public String getResponseCode() {
        return responseCode;
    }
    
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    public String getResponseMessage() {
        return responseMessage;
    }
}
