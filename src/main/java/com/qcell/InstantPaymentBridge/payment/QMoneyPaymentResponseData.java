package com.qcell.InstantPaymentBridge.payment;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class QMoneyPaymentResponseData {

   @SerializedName("transactionId")
   String transactionId;

   @SerializedName("balanceData")
   List<BalanceData> balanceData;


    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setBalanceData(List<BalanceData> balanceData) {
        this.balanceData = balanceData;
    }
    public List<BalanceData> getBalanceData() {
        return balanceData;
    }
}
