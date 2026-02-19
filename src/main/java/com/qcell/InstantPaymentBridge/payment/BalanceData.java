package com.qcell.InstantPaymentBridge.payment;

import com.google.gson.annotations.SerializedName;

public class BalanceData {
    
   @SerializedName("walletExternalId")
   String walletExternalId;

   @SerializedName("usedValue")
   int usedValue;

   @SerializedName("unusedValue")
   int unusedValue;

   @SerializedName("availableBalance")
   String availableBalance;

   @SerializedName("pouchExternalId")
   String pouchExternalId;

   @SerializedName("ValidFromDate")
   String ValidFromDate;

   @SerializedName("ValidToDate")
   String ValidToDate;


    public void setWalletExternalId(String walletExternalId) {
        this.walletExternalId = walletExternalId;
    }
    public String getWalletExternalId() {
        return walletExternalId;
    }
    
    public void setUsedValue(int usedValue) {
        this.usedValue = usedValue;
    }
    public int getUsedValue() {
        return usedValue;
    }
    
    public void setUnusedValue(int unusedValue) {
        this.unusedValue = unusedValue;
    }
    public int getUnusedValue() {
        return unusedValue;
    }
    
    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }
    public String getAvailableBalance() {
        return availableBalance;
    }
    
    public void setPouchExternalId(String pouchExternalId) {
        this.pouchExternalId = pouchExternalId;
    }
    public String getPouchExternalId() {
        return pouchExternalId;
    }
    
    public void setValidFromDate(String ValidFromDate) {
        this.ValidFromDate = ValidFromDate;
    }
    public String getValidFromDate() {
        return ValidFromDate;
    }
    
    public void setValidToDate(String ValidToDate) {
        this.ValidToDate = ValidToDate;
    }
    public String getValidToDate() {
        return ValidToDate;
    }
}
