package com.esprit.animatemymeal.Utils;

import android.app.Application;

/**
 * Created by xagta on 21/08/2017.
 */

public class ApiSingleton extends Application {

    public static ApiInterface ApiInterface;
    public static ApiSingleton mInstance;

    public static ApiSingleton getInstance() {
        if (mInstance == null)
            mInstance = new ApiSingleton();

        return mInstance;
    }

    public ApiInterface getApi() {
        if (ApiInterface == null)
            return ApiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        return ApiInterface;
    }


}
