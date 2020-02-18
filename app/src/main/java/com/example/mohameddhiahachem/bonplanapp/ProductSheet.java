package com.example.mohameddhiahachem.bonplanapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohameddhiahachem.bonplanapp.DB.DBHelper;
import com.example.mohameddhiahachem.bonplanapp.Entity.ProductInfo;
import com.example.mohameddhiahachem.bonplanapp.Entity.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductSheet extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView name,price, likes, description, address;
    private ImageView image;
    private String id;
    private ProductInfo[] products;
    private ProductInfo productInfo;
    private FloatingActionButton like;
    private DBHelper db;
    private User user;
    private boolean isLikedByUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sheet);

        db = new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        id = extras.getString("ID");
        Toast.makeText(getApplicationContext(), "Product Id: " + id , Toast.LENGTH_SHORT).show();
        name = (TextView) findViewById(R.id.p_sheet_name);
        price = (TextView) findViewById(R.id.p_sheet_price);
        description = (TextView) findViewById(R.id.p_sheet_description);
        address = (TextView) findViewById(R.id.p_sheet_address);
        image = (ImageView) findViewById(R.id.p_sheet_image);
        like = (FloatingActionButton) findViewById(R.id.p_sheet_like);
        likes = (TextView) findViewById(R.id.p_sheet_likes);
        toolbar = (Toolbar) findViewById(R.id.p_sheet_toolbar);
        setSupportActionBar(toolbar);
        user = db.getUser();
        loadData();
        isLiked();

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isLikedByUser){
                    like.setImageResource(R.drawable.is_not_liked);
                    isLikedByUser = false;
                }else{
                    like.setImageResource(R.drawable.is_liked);
                    isLikedByUser = true;
                }


                String URL = "http://10.0.3.2:8000/api/product/"+ id + "/like";

                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            int code = jsonObject.getInt("code");
                            int peopleLikes = jsonObject.getInt("likes");
                            if(code == 200){
                                likes.setText(peopleLikes +" People liked");
                                Toast.makeText(getApplicationContext(), "Like bien supprimé", Toast.LENGTH_SHORT).show();
                            }else if( code == 201 ){
                                likes.setText(peopleLikes +" People liked");
                                Toast.makeText(getApplicationContext(), "Like bien ajoute", Toast.LENGTH_SHORT).show();
                            }



                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Server busy try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("email", user.getEmail());
                        params.put("password", user.getPassword());
                        return params;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(
                        30000,
                        2,
                        1f));

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public void loadData(){
        String URL_REGIST = "http://10.0.3.2:8000/api/product/" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("code");
                    if(success){
                        Gson gson = new Gson();
                        products = gson.fromJson(jsonObject.getJSONArray("product").toString(), ProductInfo[].class);
                        for( ProductInfo p : products){
                            productInfo = new ProductInfo(p.getId(), p.getName(),p.getDescription(),p.getImage(),p.getPrice(),p.getTelephone(),p.getPlan(),p.getEmail(),p.getAddress(),p.getCity(),p.getGovernorate());
                        }

                        //Toast.makeText(getApplicationContext(), productInfo.toString(), Toast.LENGTH_LONG).show();
                        name.setText(productInfo.getName());
                        price.setText(productInfo.getPrice() + " DT");
                        description.setText(productInfo.getDescription());
                        address.setText(productInfo.getAddress() + ", " + productInfo.getCity() + ", " + productInfo.getGovernorate());
                        Picasso.get().load("http://10.0.3.2:8000/uploads/products/" + productInfo.getImage()).resize(150,150).into(image);

                    }else{
                        Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Server busy try again", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                2,
                1f));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    public void isLiked(){
        String URL = "http://10.0.3.2:8000/api/liked/product/"+ id;

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int peopleLikes = jsonObject.getInt("likes");
                    likes.setText(peopleLikes +" People liked");
                    boolean isLiked = jsonObject.getBoolean("liked");
                    if(isLiked){
                        like.setImageResource(R.drawable.is_liked);
                        isLikedByUser = true;
                    }



                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Server busy try again", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                2,
                1f));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }


}
