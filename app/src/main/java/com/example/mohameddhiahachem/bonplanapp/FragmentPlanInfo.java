package com.example.mohameddhiahachem.bonplanapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.example.mohameddhiahachem.bonplanapp.Adapter.EventAdapter;
import com.example.mohameddhiahachem.bonplanapp.DB.DBHelper;
import com.example.mohameddhiahachem.bonplanapp.Entity.EventPlan;
import com.example.mohameddhiahachem.bonplanapp.Entity.PlanInfo;
import com.example.mohameddhiahachem.bonplanapp.Entity.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("ValidFragment")
public class FragmentPlanInfo extends Fragment {

    private View view;
    private String id ;
    private TextView name, description , address, startDay,finalDay,startTime,finalTime;
    private Button call, review, send_message;
    private PlanInfo planInfo;
    PlanInfo[] plans;
    HashMap<Integer, String> horaires = new HashMap<>();
    private DBHelper db;
    private User user;


    @SuppressLint("ValidFragment")
    public FragmentPlanInfo(String id){
        this.id = id;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan_info, container, false);


        name = (TextView) view.findViewById(R.id.plan_info_name);
        description = (TextView) view.findViewById(R.id.plan_info_description);
        address = (TextView) view.findViewById(R.id.plan_info_address);
        startDay = (TextView) view.findViewById(R.id.plan_info_start_day);
        finalDay = (TextView) view.findViewById(R.id.plan_info_final_day);
        startTime = (TextView) view.findViewById(R.id.plan_info_start_time);
        finalTime = (TextView) view.findViewById(R.id.plan_info_final_time);
        call = (Button) view.findViewById(R.id.plan_info_call);
        review = (Button) view.findViewById(R.id.plan_info_avis);
        send_message = (Button) view.findViewById(R.id.plan_info_send_message);
        db = new DBHelper(getContext());
        user = db.getUser();
        loadData();


        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogReview();
            }
        });

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogSend();
            }
        });




        return view;
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
                        Toast.makeText(getContext(), "set info Plan", Toast.LENGTH_SHORT).show();
                        horaires.put(0, "Monday");
                        horaires.put(1, "Tuesday");
                        horaires.put(2, "Wednesday");
                        horaires.put(3, "Thursday");
                        horaires.put(4, "Friday");
                        horaires.put(5, "Saturday");
                        horaires.put(6, "Sunday");

                        name.setText(planInfo.getName());
                        description.setText(planInfo.getDescription());
                        address.setText(planInfo.getAddress());
                        startDay.setText(horaires.get(Integer.valueOf(planInfo.getStartDay())) + " - ");
                        finalDay.setText(horaires.get(Integer.valueOf(planInfo.getFinalDay())));
                        startTime.setText(planInfo.getStartTime().substring(11,16) + " - ");
                        finalTime.setText(planInfo.getFinalTime().substring(11,16));

                        call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+ planInfo.getTelephone()));
                                startActivity(intent);
                            }
                        });

                    }else{
                        Toast.makeText(getContext(), "not good", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getContext(), "Erorr Eroor 1 !!!!!!!!!!!!!!!!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Erorr Eroor 2!!!!!!!!!!!!!!!!!!" + error.toString(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void ShowDialogReview() {

        final AlertDialog.Builder mB = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.activity_rating, null);

        ImageView ratingClose = (ImageView) view.findViewById(R.id.rating_close);
        Button submit = (Button) view.findViewById(R.id.rating_button);
        final EditText descripton = (EditText) view.findViewById(R.id.rating_description);
        final RatingBar rating = (RatingBar) view.findViewById(R.id.f_rating_star);
        mB.setView(view);
        final AlertDialog dialog = mB.create();

        ratingClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL_REGIST = "http://10.0.3.2:8000/api/p/rating/" + planInfo.getId();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("code");
                            if(success){
                                Toast.makeText(getContext(), "good", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(getContext(), "not good", Toast.LENGTH_SHORT).show();
                            }

                            dialog.dismiss();

                        }catch (Exception e){
                            Toast.makeText(getContext(), "Erorr Eroor 1 !!!!!!!!!!!!!!!!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "Erorr Eroor 2!!!!!!!!!!!!!!!!!!" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("email",user.getEmail() );
                        params.put("password", user.getPassword());
                        params.put("content", descripton.getText().toString());
                        params.put("rating", String.valueOf(rating.getNumStars()));
                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        30000,
                        2,
                        1f));

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }
        });


    }

    private void ShowDialogSend() {

        final AlertDialog.Builder mB = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.activity_send_message, null);

        ImageView ratingClose = (ImageView) view.findViewById(R.id.send_message_close);
        Button submit = (Button) view.findViewById(R.id.send_message_button);
        final EditText desc = (EditText) view.findViewById(R.id.send_message_description);
        mB.setView(view);
        final AlertDialog dialog = mB.create();




        dialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL_REGIST = "http://10.0.3.2:8000/api/send/message";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("code");
                            if(success){
                                Toast.makeText(getContext(), "good", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(getContext(), "not good", Toast.LENGTH_SHORT).show();
                            }

                            dialog.dismiss();

                        }catch (Exception e){
                            Toast.makeText(getContext(), "Erorr Eroor 1 !!!!!!!!!!!!!!!!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "Erorr Eroor 2!!!!!!!!!!!!!!!!!!" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("email",user.getEmail() );
                        params.put("content", desc.getText().toString());
                        params.put("password", user.getPassword());
                        params.put("receivere", planInfo.getEmail());
                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        30000,
                        2,
                        1f));

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }
        });


    }



}
