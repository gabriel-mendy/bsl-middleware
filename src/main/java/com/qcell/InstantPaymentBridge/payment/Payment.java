package com.qcell.InstantPaymentBridge.payment;

import com.google.gson.annotations.SerializedName;

public class Payment {
    
       @SerializedName("amount")
   String amount;


    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getAmount() {
        return amount;
    }
}
