package com.qcell.InstantPaymentBridge.entity;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

   
public class Customer {

   @SerializedName("id")
   int id;

   @SerializedName("name")
   String name;

   @SerializedName("surname")
   String surname;

   @SerializedName("nickName")
   String nickName;

   @SerializedName("gender")
   String gender;

   @SerializedName("birthDate")
   Date birthDate;

   @SerializedName("document")
   String document;

   @SerializedName("type")
   String type;

   @SerializedName("participant")
   String participant;

   @SerializedName("mobileNumber")
   String mobileNumber;

   @SerializedName("documentValidityDate")
   Date documentValidityDate;

   @SerializedName("customerForAlias")
   String customerForAlias;


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getSurname() {
        return surname;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getNickName() {
        return nickName;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return gender;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setDocument(String document) {
        this.document = document;
    }
    public String getDocument() {
        return document;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    
    public void setParticipant(String participant) {
        this.participant = participant;
    }
    public String getParticipant() {
        return participant;
    }
    
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    
    public void setDocumentValidityDate(Date documentValidityDate) {
        this.documentValidityDate = documentValidityDate;
    }
    public Date getDocumentValidityDate() {
        return documentValidityDate;
    }
    
    public void setCustomerForAlias(String customerForAlias) {
        this.customerForAlias = customerForAlias;
    }
    public String getCustomerForAlias() {
        return customerForAlias;
    }
    
}