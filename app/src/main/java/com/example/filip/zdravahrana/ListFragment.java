package com.example.filip.zdravahrana;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Filip on 12/19/2018.
 */

public class ListFragment extends Fragment {
    private ShopData shopPrices;
    private RecyclerView shopListRecycler;
    private ShopListRecyclerAdapter shopListRecyclerAdapter;
    private String  name;
    public ListFragment() {
    }


    @SuppressLint("ValidFragment")
    public ListFragment(String name, ShopData prices) {
        this.name = name;
        this.shopPrices = prices;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        TextView shopName = view.findViewById(R.id.listFragmentName);
        shopName.setText(name);
        shopListRecyclerAdapter = new ShopListRecyclerAdapter();
        shopListRecycler = view.findViewById(R.id.shopListRecycler);
        shopListRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        shopListRecycler.setAdapter(shopListRecyclerAdapter);
        shopListRecyclerAdapter.notifyDataSetChanged();
        return view;
    }
}
