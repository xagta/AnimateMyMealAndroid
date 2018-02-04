package com.esprit.animatemymeal.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.esprit.animatemymeal.Entities.Restaurant;
import com.esprit.animatemymeal.Entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by xagta on 22/08/2017.
 */

public class RestaurantProvider {
    public ApiInterface apiInterface = ApiSingleton.getInstance().getApi();

    public void getAllRestaurants(final OnRestaurantReady listner) {
        Call<List<Restaurant>> call = apiInterface.getRestaurants(); 
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {

                listner.OnRestaurantReady(response.body());
            }

            @Override

            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

                Log.d("RestaurantLoad Failed",t.getMessage());
                System.out.printf("RestaurantLoad Failed");

            }
        });


    }
    public interface OnRestaurantReady {
        void OnRestaurantReady(List<Restaurant> body);
    }
}
