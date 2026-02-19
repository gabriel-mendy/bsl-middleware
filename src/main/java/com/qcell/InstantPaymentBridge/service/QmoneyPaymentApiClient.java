package com.qcell.InstantPaymentBridge.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.concurrent.TimeUnit;

public class QmoneyPaymentApiClient {

    //https://uat-adpelite.qmoney.sl/domesticSendMoney
   // private static final String BASE_URL = "https://uat-adpelite.qmoney.sl";

    //https://adpelite.qmoney.sl
    private static final String BASE_URL = "https://adpelite.qmoney.sl";

    private static Retrofit mRetrofit = null;

    public static Retrofit getPaymentGwClient() {


        Gson gson1;
        gson1 = new GsonBuilder().setLenient().create();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client;
        client = new OkHttpClient.Builder()
                //.proxy(proxy)
                //.proxyAuthenticator(proxyAuthenticator)
                // .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                // .hostnameVerifier { _, _ -> true }
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();


        if (mRetrofit == null) {
            mRetrofit =
                    new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create(gson1))
                            .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                            .addConverterFactory(ScalarsConverterFactory.create())
                            //.addConverterFactory(JaxbConverterFactory.create())
                            .build();
        }

        return mRetrofit;
    }
}
