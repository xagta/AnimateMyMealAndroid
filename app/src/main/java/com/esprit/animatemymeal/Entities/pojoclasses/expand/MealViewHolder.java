package com.esprit.animatemymeal.Entities.pojoclasses.expand;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by xagta on 29/01/2018.
 */

public class MealViewHolder extends ChildViewHolder {
    private TextView mealName ;
    private TextView mealPrice ;

    public MealViewHolder(View itemView) {
        super(itemView);
    }

    public void onBind(CategoryExpandable categoryExpandable)
    {
        mealName.setText(categoryExpandable.getTitle());
    }
}
