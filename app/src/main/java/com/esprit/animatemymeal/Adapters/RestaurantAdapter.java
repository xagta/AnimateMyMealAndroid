package com.esprit.animatemymeal.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.animatemymeal.Entities.Restaurant;
import com.esprit.animatemymeal.Fragments.CategoriesFragment;
import com.esprit.animatemymeal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by xagta on 27/01/2018.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantAdapterViewHolder> {

    private Context mContext ;
    List<Restaurant> mList ;



    public RestaurantAdapter(Context mContext,List<Restaurant> mList)
    {
        this.mContext= mContext ;
        this.mList = mList ;

    }

    @Override
    public RestaurantAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurantlayout_item, parent, false);
        RestaurantAdapterViewHolder ravh = new RestaurantAdapterViewHolder(v);

    return  ravh ;
    }

    @Override
    public void onBindViewHolder(RestaurantAdapterViewHolder holder, final int position) {


        holder.resto_name.setText(mList.get(position).getName());
     //   holder.resto_image.setI(mList.get(position).getName());

        Picasso.with(mContext).load(mList.get(position).getImgUrl()).placeholder(R.drawable.ic_home_black_24dp).fit().into(holder.resto_image);
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager= ((AppCompatActivity) view.getContext()).getSupportFragmentManager() ;

                Bundle bundle = new Bundle() ;
                bundle.putString("restaurantId", String.valueOf(mList.get(position).getId()));
                CategoriesFragment categoriesFragment = CategoriesFragment.newInstance() ;
                categoriesFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.frameLayout, categoriesFragment).addToBackStack("a").commit() ;
            }
        });
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RestaurantAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView resto_name ;
        ImageView resto_image ;
        CardView card_view ;
        public RestaurantAdapterViewHolder(View itemView) {
            super(itemView);

            resto_name = (TextView) itemView.findViewById(R.id.restaurant_name) ;
            resto_image = (ImageView) itemView.findViewById(R.id.restaurant_img) ;
            card_view = (CardView) itemView.findViewById(R.id.card_view);


        }
    }
}
