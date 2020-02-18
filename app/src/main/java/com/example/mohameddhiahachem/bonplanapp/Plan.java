package com.example.mohameddhiahachem.bonplanapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
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
import com.bumptech.glide.Glide;
import com.example.mohameddhiahachem.bonplanapp.Entity.PlanInfo;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Plan extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private String id ="";
    private PlanInfo planInfo = null;
    PlanInfo[] plans;
    private ImageView imageView;
    private FloatingActionButton rating;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_navigation);


        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        imageView = (ImageView) findViewById(R.id.plan_sheet_info_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.plan_sheet_toolbar);
        setSupportActionBar(toolbar);
        loadData();



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


        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        adapter.AddFragment(new FragmentPlanInfo(id), "info");
        adapter.AddFragment(new FragmentPlanProduct(id), "products");
        adapter.AddFragment(new FragmentPlanEvent(id), "events");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }




    public void loadData(){
        String URL_REGIST = "http://10.0.3.2:8000/api/" + id + "/plan";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("code");
                    if(success){
                        Gson gson = new Gson();
                        plans = gson.fromJson(jsonObject.getJSONArray("plan").toString(), PlanInfo[].class);
                        for( PlanInfo plan : plans){
                            planInfo = new PlanInfo(plan.getId(), Jsoup.parse(plan.getDescription()).text(),plan.getStartDay(), plan.getFinalDay(),plan.getStartTime(),plan.getFinalTime(),plan.getName(),plan.getAddress(),plan.getCity(),plan.getGovernorate(),plan.getImage(), plan.getTelephone(),plan.getEmail());
                        }
                        //Toast.makeText(getApplicationContext(), "set Image Plan" + planInfo.getImage(), Toast.LENGTH_SHORT).show();
                        //Picasso.get().load("http://10.0.3.2:8000/uploads/plans/" + planInfo.getImage()).into(imageView);
                        //Glide.with(getApplicationContext()).load("http://10.0.3.2:8000/uploads/plans/" + planInfo.getImage()).into(imageView);
                        Picasso.get()
                                .load("http://10.0.3.2:8000/uploads/plans/" + planInfo.getImage())

            .resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight()) // <-- is needed for some functions

                                .into(imageView);
                    }else{
                        Toast.makeText(getApplicationContext(), "not good", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Erorr Eroor !!!!!!!!!!!!!!!!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Erorr Eroor !!!!!!!!!!!!!!!!!!" + error.toString(), Toast.LENGTH_SHORT).show();
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

}
