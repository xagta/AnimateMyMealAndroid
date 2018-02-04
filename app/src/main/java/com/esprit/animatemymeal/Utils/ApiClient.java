package com.esprit.animatemymeal.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xagta on 17/08/2017.
 */

public class ApiClient {

   //  public static final String BASE_URL = "http://192.168.1.7/youserve/v2/";
    public static final String BASE_URL = "http://172.20.10.2/api/";

    //  public static final String BASE_URL = "https://youserve-safwaneettih924628.codeanyapp.com/";
    public static Retrofit retrofit = null;


    public static Retrofit getApiClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);

// add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!


        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;
    }




}