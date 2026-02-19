package com.qcell.InstantPaymentBridge.entity;

import com.google.gson.annotations.SerializedName;

public class NSCertificateResponse {

    @SerializedName("certificate" )
    String certificate = null;

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
