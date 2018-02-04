package com.esprit.animatemymeal.Utils;

import com.esprit.animatemymeal.Entities.Restaurant;
import com.esprit.animatemymeal.Entities.pojoclasses.Category;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by xagta on 17/08/2017.
 */

public interface ApiInterface {

    @GET("restaurant")
    Call<List<Restaurant>> getRestaurants();
    @GET("getrestaurantcategories/{id_resto}")
    Call<List<Category>> getCategories(@Path("id_resto") String id_resto);





}
