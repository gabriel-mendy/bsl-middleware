package com.qcell.InstantPaymentBridge.payment;

import com.google.gson.annotations.SerializedName;

public class QMoneyPaymentRequest {
    
       @SerializedName("data")
       QMoneyPaymentRequestData data;


    public void setData(QMoneyPaymentRequestData data) {
        this.data = data;
    }
    public QMoneyPaymentRequestData getData() {
        return data;
    }
}
