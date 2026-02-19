package com.qcell.InstantPaymentBridge.payment;

import com.google.gson.annotations.SerializedName;

public class QMoneyLoginRequest {

    
   @SerializedName("data")
   QMoneyLoginRequestData data;


    public void setData(QMoneyLoginRequestData data) {
        this.data = data;
    }
    public QMoneyLoginRequestData getData() {
        return data;
    }
}
