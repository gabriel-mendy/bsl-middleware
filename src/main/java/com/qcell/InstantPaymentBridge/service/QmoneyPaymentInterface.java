package com.qcell.InstantPaymentBridge.service;

import com.qcell.InstantPaymentBridge.entity.NSCertificateRequest;
import com.qcell.InstantPaymentBridge.entity.NSCertificateResponse;
import com.qcell.InstantPaymentBridge.entity.NSCustomerAliasRequest;
import com.qcell.InstantPaymentBridge.entity.NSCustomerAliasResponse;
import com.qcell.InstantPaymentBridge.payment.QMoneyLoginRequest;
import com.qcell.InstantPaymentBridge.payment.QMoneyLoginResponse;
import com.qcell.InstantPaymentBridge.payment.QMoneyPaymentRequest;
import com.qcell.InstantPaymentBridge.payment.QMoneyPaymentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

import java.util.HashMap;

public interface QmoneyPaymentInterface {

    //https://uat-adpelite.qmoney.sl/login
    @POST("/login")
    Call<QMoneyLoginResponse> getLoginToken(@HeaderMap HashMap<String, String> headers, @Body QMoneyLoginRequest loginRequest);

    //https://uat-adpelite.qmoney.sl/domesticSendMoney
    @POST("/domesticSendMoney")
    Call<QMoneyPaymentResponse> sendPayment(@HeaderMap HashMap<String, String> headers, @Body QMoneyPaymentRequest paymentRequest);
}