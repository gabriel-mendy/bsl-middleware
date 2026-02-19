package com.qcell.InstantPaymentBridge.payment;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class QMoneyPaymentRequestData {

       @SerializedName("fromUser")
   FromUser fromUser;

   @SerializedName("toUser")
   ToUser toUser;

   @SerializedName("serviceId")
   String serviceId;

   @SerializedName("productId")
   String productId;

   @SerializedName("switchtxnid")
   String switchtxnid;

   @SerializedName("txnstatus")
   String txnstatus;

   @SerializedName("remarks")
   String remarks;

   @SerializedName("payment")
   List<Payment> payment;

   @SerializedName("transactionPin")
   String transactionPin;


    public void setFromUser(FromUser fromUser) {
        this.fromUser = fromUser;
    }
    public FromUser getFromUser() {
        return fromUser;
    }
    
    public void setToUser(ToUser toUser) {
        this.toUser = toUser;
    }
    public ToUser getToUser() {
        return toUser;
    }
    
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    public String getServiceId() {
        return serviceId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductId() {
        return productId;
    }
    
    public void setSwitchtxnid(String switchtxnid) {
        this.switchtxnid = switchtxnid;
    }
    public String getSwitchtxnid() {
        return switchtxnid;
    }
    
    public void setTxnstatus(String txnstatus) {
        this.txnstatus = txnstatus;
    }
    public String getTxnstatus() {
        return txnstatus;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getRemarks() {
        return remarks;
    }
    
    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }
    public List<Payment> getPayment() {
        return payment;
    }
    
    public void setTransactionPin(String transactionPin) {
        this.transactionPin = transactionPin;
    }
    public String getTransactionPin() {
        return transactionPin;
    }

}
