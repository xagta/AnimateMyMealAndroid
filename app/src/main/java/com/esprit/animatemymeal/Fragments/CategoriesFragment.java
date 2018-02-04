package com.esprit.animatemymeal.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.animatemymeal.Adapters.CategoryAdapter;
import com.esprit.animatemymeal.Adapters.RestaurantAdapter;
import com.esprit.animatemymeal.Entities.Restaurant;
import com.esprit.animatemymeal.Entities.pojoclasses.Category;
import com.esprit.animatemymeal.R;
import com.esprit.animatemymeal.Utils.CategoryProvider;
import com.esprit.animatemymeal.Utils.RestaurantProvider;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    RecyclerView CategoryRecyclerView;

    // TODO: Rename and change types of parameters



    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=   inflater.inflate(R.layout.categories_fragment, container, false);
        CategoryRecyclerView = view.findViewById(R.id.cats_rv) ;

        String resto_id = getArguments().getString("restaurantId") ;

        LinearLayoutManager llm = new LinearLayoutManager(getContext());

        CategoryRecyclerView.setLayoutManager(llm);
        CategoryRecyclerView.setHasFixedSize(true);

        CategoryProvider provider = new CategoryProvider() ;

        provider.getCategories(new CategoryProvider.OnCategoryReponse() {
            @Override
            public void OnCategoryReady(List<Category> body) {
                CategoryAdapter adapter = new CategoryAdapter(getContext(),body) ;
                CategoryRecyclerView.setAdapter(adapter);
            }
        }, resto_id);

        return view ;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
