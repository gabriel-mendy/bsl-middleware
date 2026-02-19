package com.qcell.InstantPaymentBridge.entity;

import com.google.gson.annotations.SerializedName;

   
public class NSResponse {

   @SerializedName("id")
   int id;

   @SerializedName("iban")
   String iban;

   @SerializedName("accountNumber")
   String accountNumber;

   @SerializedName("status")
   int status;

   @SerializedName("technicalCounter")
   String technicalCounter;

   @SerializedName("overDraftLimit")
   String overDraftLimit;

   @SerializedName("netPosition")
   String netPosition;

   @SerializedName("minimumDeposit")
   String minimumDeposit;

   @SerializedName("balance")
   String balance;

   @SerializedName("type")
   int type;

   @SerializedName("remainingBalance")
   String remainingBalance;

   @SerializedName("lowLimit")
   String lowLimit;

   @SerializedName("baseLimit")
   String baseLimit;

   @SerializedName("highLimit")
   String highLimit;

   @SerializedName("currency")
   String currency;

   @SerializedName("bic")
   String bic;

   @SerializedName("customer")
   Customer customer;

   @SerializedName("default")
   boolean default1;


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    
    public void setIban(String iban) {
        this.iban = iban;
    }
    public String getIban() {
        return iban;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }
    
    public void setTechnicalCounter(String technicalCounter) {
        this.technicalCounter = technicalCounter;
    }
    public String getTechnicalCounter() {
        return technicalCounter;
    }
    
    public void setOverDraftLimit(String overDraftLimit) {
        this.overDraftLimit = overDraftLimit;
    }
    public String getOverDraftLimit() {
        return overDraftLimit;
    }
    
    public void setNetPosition(String netPosition) {
        this.netPosition = netPosition;
    }
    public String getNetPosition() {
        return netPosition;
    }
    
    public void setMinimumDeposit(String minimumDeposit) {
        this.minimumDeposit = minimumDeposit;
    }
    public String getMinimumDeposit() {
        return minimumDeposit;
    }
    
    public void setBalance(String balance) {
        this.balance = balance;
    }
    public String getBalance() {
        return balance;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
    
    public void setRemainingBalance(String remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
    public String getRemainingBalance() {
        return remainingBalance;
    }
    
    public void setLowLimit(String lowLimit) {
        this.lowLimit = lowLimit;
    }
    public String getLowLimit() {
        return lowLimit;
    }
    
    public void setBaseLimit(String baseLimit) {
        this.baseLimit = baseLimit;
    }
    public String getBaseLimit() {
        return baseLimit;
    }
    
    public void setHighLimit(String highLimit) {
        this.highLimit = highLimit;
    }
    public String getHighLimit() {
        return highLimit;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getCurrency() {
        return currency;
    }
    
    public void setBic(String bic) {
        this.bic = bic;
    }
    public String getBic() {
        return bic;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Customer getCustomer() {
        return customer;
    }
    
    public void setDefault(boolean default1) {
        this.default1 = default1;
    }
    public boolean getDefault() {
        return default1;
    }
    
}