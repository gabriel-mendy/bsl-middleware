package com.qcell.InstantPaymentBridge.payment;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class QMoneyLoginRequestData {

        @SerializedName("grantType")
        String grantType;
     
        @SerializedName("username")
        String username;
     
        @SerializedName("password")
        String password;
     
     
         public void setGrantType(String grantType) {
             this.grantType = grantType;
         }
         public String getGrantType() {
             return grantType;
         }
         
         public void setUsername(String username) {
             this.username = username;
         }
         public String getUsername() {
             return username;
         }
         
         public void setPassword(String password) {
             this.password = password;
         }
         public String getPassword() {
             return password;
         }
}