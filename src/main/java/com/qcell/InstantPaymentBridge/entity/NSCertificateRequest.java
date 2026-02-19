package com.qcell.InstantPaymentBridge.entity;

import com.google.gson.annotations.SerializedName;

public class NSCertificateRequest {

    @SerializedName("serial_number")
    String serialNumber = null;
    @SerializedName("issuer")
    String issuer  = null;
    @SerializedName("certificate")
    String certificate = null;
    @SerializedName("error")
    String error  = null;


    public NSCertificateRequest(String issuer, String serialNumber, String certificate, String error) {
        this.issuer = issuer;
        this.serialNumber = serialNumber;
        this.certificate = certificate;
        this.error = error;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
