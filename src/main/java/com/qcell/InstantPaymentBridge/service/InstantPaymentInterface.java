package com.qcell.InstantPaymentBridge.service;

import com.qcell.InstantPaymentBridge.entity.NSCertificateRequest;
import com.qcell.InstantPaymentBridge.entity.NSCertificateResponse;
import com.qcell.InstantPaymentBridge.entity.NSCustomerAliasRequest;
import com.qcell.InstantPaymentBridge.entity.NSCustomerAliasResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

import java.util.HashMap;

public interface InstantPaymentInterface {
    // 3 - NS Push Payment /v1/iso20022/incoming
    @POST("/v1/iso20022/incoming")
    Call<String> pushPayment(@HeaderMap HashMap<String, String> headers , @Body String  nspaymentdata);

    //http://172.16.10.177:9001/v1/cert
    //{
    //  "serial_number": "2274676010378155247360161688928419467193483288",
    //  "issuer": "CN=BSL-ISSUER-CA01, DC=saps, DC=gov, DC=sl",
    //  "certificate": "",
    //  "error": ""
    //}

    //@HTTP(method = "get", path = "/v1/cert", hasBody = true)
    @POST("/v1/cert")
    Call<NSCertificateResponse> getCertificate(@HeaderMap HashMap<String, String> headers, @Body NSCertificateRequest certdata);


    //curl -v -X POST 'http://172.16.10.177:23473/api/fp/customer/accounts' -H 'Content-Type:application/json' -d '{"mobileNumber":"23275636212"}'
    @POST("/api/fp/customer/accounts")
    Call<NSCustomerAliasResponse> getAccountByPhoneNumber(@HeaderMap  HashMap<String, String> headers, @Body NSCustomerAliasRequest accountByPhoneNumberData);

}