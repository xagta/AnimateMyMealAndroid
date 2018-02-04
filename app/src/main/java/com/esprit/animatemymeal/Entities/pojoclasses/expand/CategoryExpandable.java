package com.esprit.animatemymeal.Entities.pojoclasses.expand;

import com.esprit.animatemymeal.Entities.pojoclasses.Meal;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by xagta on 29/01/2018.
 */

public class CategoryExpandable  extends ExpandableGroup<Meal> {
    public CategoryExpandable(String title, List<Meal> items) {
        super(title, items);
    }
}
