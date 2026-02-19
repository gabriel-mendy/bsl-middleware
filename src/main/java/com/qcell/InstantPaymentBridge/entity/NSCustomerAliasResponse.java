package com.qcell.InstantPaymentBridge.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NSCustomerAliasResponse {

    @SerializedName("errorCode")
    String errorCode;

    @SerializedName("errorDescription")
    String errorDescription;

    @SerializedName("description")
    String description;

    @SerializedName("response")
    List<NSResponse> response;


    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
    public String getErrorDescription() {
        return errorDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setResponse(List<NSResponse> response) {
        this.response = response;
    }
    public List<NSResponse> getResponse() {
        return response;
    }


}
