package com.example.filip.zdravahrana;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Filip on 12/18/2018.
 */

public class ShopListRecyclerAdapter extends RecyclerView.Adapter<ShopListRecyclerAdapter.ViewHolder>  {

    private LayoutInflater layoutInflater;
    private ArrayList<MyShopObject> prices = new ArrayList<>();
    private View view;

    public ShopListRecyclerAdapter() {

    }

    @Override
    public ShopListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.list_item_my_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopListRecyclerAdapter.ViewHolder holder, int position) {
        MyShopObject price = prices.get(position);
        holder.bindContent(price);
        holder.title.setText(price.getTitle().replace("_"," "));
        holder.value.setText(String.valueOf(price.getValue()));

    }

    @Override
    public int getItemCount() {
        return prices.size();
    }
    public void addItem(MyShopObject shopObject) {
        prices.add(shopObject);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MyShopObject price;
        TextView title;
        TextView value;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.shopTitle);
            value = (TextView) itemView.findViewById(R.id.shopValue);
        }
        public void bindContent(MyShopObject price) {
            this.price = price;
        }
    }
}
