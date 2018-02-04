package com.esprit.animatemymeal.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.animatemymeal.Entities.Restaurant;
import com.esprit.animatemymeal.Entities.pojoclasses.Category;
import com.esprit.animatemymeal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by xagta on 27/01/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder> {

    private Context mContext ;
    List<Category> mList ;


    public CategoryAdapter(Context mContext, List<Category> mList)
    {
        this.mContext= mContext ;
        this.mList = mList ;

    }

    @Override
    public CategoryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout_item, parent, false);
        CategoryAdapterViewHolder ravh = new CategoryAdapterViewHolder(v);

    return  ravh ;
    }

    @Override
    public void onBindViewHolder(CategoryAdapterViewHolder holder, int position) {

        holder.cat_name.setText(mList.get(position).getName());
     //   holder.resto_image.setI(mList.get(position).getName());

     //   Picasso.with(mContext).load(mList.get(position).getImgUrl()).fit().into(holder.cat_image);
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CategoryAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView cat_name ;
        ImageView cat_image ;
        public CategoryAdapterViewHolder(View itemView) {
            super(itemView);

            cat_name = (TextView) itemView.findViewById(R.id.cat_name) ;
            cat_image = (ImageView) itemView.findViewById(R.id.cat_image) ;


        }
    }
}
