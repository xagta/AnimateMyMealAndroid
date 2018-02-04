package com.esprit.animatemymeal.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.animatemymeal.Adapters.RestaurantAdapter;
import com.esprit.animatemymeal.Entities.Restaurant;
import com.esprit.animatemymeal.R;
import com.esprit.animatemymeal.Utils.RestaurantProvider;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    RecyclerView RestaurantRecyclerView ;

    // TODO: Rename and change types of parameters



    public RestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantFragment newInstance() {
        RestaurantFragment fragment = new RestaurantFragment();
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
        View view=   inflater.inflate(R.layout.fragment_restaurant, container, false);
        RestaurantRecyclerView = view.findViewById(R.id.restoRV) ;

        LinearLayoutManager llm = new LinearLayoutManager(getContext());

        RestaurantRecyclerView.setLayoutManager(llm);
        RestaurantRecyclerView.setHasFixedSize(true);

        RestaurantProvider provider = new RestaurantProvider() ;

        provider.getAllRestaurants(new RestaurantProvider.OnRestaurantReady() {
            @Override
            public void OnRestaurantReady(List<Restaurant> body) {
                RestaurantAdapter restaurantAdapter = new RestaurantAdapter(getContext(),body) ;
                RestaurantRecyclerView.setAdapter(restaurantAdapter);
            }
        });

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
