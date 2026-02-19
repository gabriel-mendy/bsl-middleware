package com.qcell.InstantPaymentBridge.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.concurrent.TimeUnit;

public class InstantCasApiClient {

    //private static final String BASE_URL = "http://172.16.10.177:23473";

    private static final String BASE_URL = "http://172.16.10.123:23473";

    private static Retrofit mRetrofit = null;

    public static Retrofit getCasClient() {


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
