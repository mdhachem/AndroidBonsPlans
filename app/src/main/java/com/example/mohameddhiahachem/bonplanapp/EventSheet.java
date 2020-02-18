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
import com.example.mohameddhiahachem.bonplanapp.Entity.EventInfo;
import com.example.mohameddhiahachem.bonplanapp.Entity.ProductInfo;
import com.example.mohameddhiahachem.bonplanapp.Entity.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EventSheet extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView name,dateEvent, likes, description, address;
    private ImageView image;
    private String id;
    private EventInfo[] events;
    private EventInfo eventInfo;
    private FloatingActionButton like;
    private DBHelper db;
    private User user;
    private boolean isLikedByUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_sheet);
        db = new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        id = extras.getString("ID");
        Toast.makeText(getApplicationContext(), "Event Id: " + id , Toast.LENGTH_SHORT).show();
        name = (TextView) findViewById(R.id.e_sheet_name);
        dateEvent = (TextView) findViewById(R.id.e_sheet_date_event);
        description = (TextView) findViewById(R.id.e_sheet_description);
        address = (TextView) findViewById(R.id.e_sheet_address);
        image = (ImageView) findViewById(R.id.e_sheet_image);
        like = (FloatingActionButton) findViewById(R.id.e_sheet_like);
        likes = (TextView) findViewById(R.id.e_sheet_likes);
        toolbar = (Toolbar) findViewById(R.id.e_sheet_toolbar);
        setSupportActionBar(toolbar);
        user = db.getUser();
        loadData();
        isLiked();

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isLikedByUser){
                    like.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.is_not_liked));
                    isLikedByUser = false;
                }else{
                    like.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.is_liked));
                    isLikedByUser = true;
                }


                String URL = "http://10.0.3.2:8000/api/event/"+ id + "/like";

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
        String URL_REGIST = "http://10.0.3.2:8000/api/event/" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("code");
                    if(success){
                        Gson gson = new Gson();
                        events = gson.fromJson(jsonObject.getJSONArray("event").toString(), EventInfo[].class);
                        for( EventInfo e : events){
                            eventInfo = new EventInfo(e.getId(),e.getName(),e.getDateEvent(),e.getDescription(),e.getImage(),e.getAddress(),e.getCity(),e.getGovernorate(),e.getEmail(),e.getTelephone(),e.getPlan());
                        }

                        //Toast.makeText(getApplicationContext(), productInfo.toString(), Toast.LENGTH_LONG).show();
                        name.setText(eventInfo.getName());
                        dateEvent.setText(eventInfo.getDateEvent().substring(8,10) +"-"+ eventInfo.getDateEvent().substring(5,7) +"-"+ eventInfo.getDateEvent().substring(0,4) +" At " + eventInfo.getDateEvent().substring(11,16));
                        description.setText(eventInfo.getDescription());
                        address.setText(eventInfo.getAddress() + ", " + eventInfo.getCity() + ", " + eventInfo.getGovernorate());
                        Picasso.get().load("http://10.0.3.2:8000/uploads/events/" + eventInfo.getImage()).resize(150,150).into(image);

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
        String URL = "http://10.0.3.2:8000/api/liked/event/"+ id;

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int peopleLikes = jsonObject.getInt("likes");
                    likes.setText(peopleLikes +" People liked");
                    boolean isLiked = jsonObject.getBoolean("liked");
                    if(isLiked){
                        like.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.is_liked));
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
