package com.example.mohameddhiahachem.bonplanapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohameddhiahachem.bonplanapp.Entity.EventPlan;
import com.example.mohameddhiahachem.bonplanapp.Entity.Product;
import com.example.mohameddhiahachem.bonplanapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;

    public ProductAdapter(@NonNull Context context, @NonNull ArrayList<Product> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);

        TextView productID = (TextView) convertView.findViewById(R.id.show_product_id);
        TextView productName = (TextView) convertView.findViewById(R.id.product_name);
        TextView productPrice = (TextView) convertView.findViewById(R.id.product_price);
        TextView productAddress = (TextView) convertView.findViewById(R.id.product_address);
        ImageView productImage = (ImageView) convertView.findViewById(R.id.product_pic);

        Product productPlan = getItem(position);

        productID.setText(String.valueOf(productPlan.getId()));
        productName.setText(productPlan.getName());
        productAddress.setText(productPlan.getAddress() + ", " + productPlan.getCity() + ", " + productPlan.getGovernorate());
        productPrice.setText(productPlan.getPrice() + " DT");

        Picasso.get().load("http://10.0.3.2:8000/uploads/products/" + productPlan.getImage()).resize(100,100).into(productImage);

        return convertView;
    }
}