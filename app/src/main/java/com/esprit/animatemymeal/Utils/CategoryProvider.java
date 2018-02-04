package com.esprit.animatemymeal.Utils;

import android.util.Log;

import com.esprit.animatemymeal.Entities.Restaurant;
import com.esprit.animatemymeal.Entities.pojoclasses.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by xagta on 22/08/2017.
 */

public class CategoryProvider {
    public ApiInterface apiInterface = ApiSingleton.getInstance().getApi();

    public void getCategories(final OnCategoryReponse listner,String id_resto) {
        Call<List<Category>> call = apiInterface.getCategories(id_resto);
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                listner.OnCategoryReady(response.body());
            }

            @Override

            public void onFailure(Call<List<Category>> call, Throwable t) {

                Log.d("RestaurantLoad Failed",t.getMessage());
                System.out.printf("RestaurantLoad Failed");

            }
        });


    }
    public interface OnCategoryReponse {
        void OnCategoryReady(List<Category> body);
    }
}
