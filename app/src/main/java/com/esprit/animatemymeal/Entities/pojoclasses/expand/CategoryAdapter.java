package com.esprit.animatemymeal.Entities.pojoclasses.expand;

import android.view.View;
import android.view.ViewGroup;

import com.esprit.animatemymeal.Entities.pojoclasses.Meal;
import com.esprit.animatemymeal.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by xagta on 29/01/2018.
 */

public class CategoryAdapter extends ExpandableRecyclerViewAdapter<CategoryViewHolder,MealViewHolder> {
    public CategoryAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
      return null ;

    }

    @Override
    public MealViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {

        return null ;
    }

    @Override
    public void onBindChildViewHolder(MealViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {

    }
}
