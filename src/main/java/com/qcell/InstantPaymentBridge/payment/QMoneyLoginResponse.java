package com.qcell.InstantPaymentBridge.payment;

import com.google.gson.annotations.SerializedName;

public class QMoneyLoginResponse {

   @SerializedName("data")
   QMoneyLoginResponseData data;

   @SerializedName("responseCode")
   String responseCode;

   @SerializedName("responseMessage")
   String responseMessage;

   @SerializedName("tenantId")
   String tenantId;


    public void setData(QMoneyLoginResponseData data) {
        this.data = data;
    }
    public QMoneyLoginResponseData getData() {
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
    
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
    public String getTenantId() {
        return tenantId;
    }

}
