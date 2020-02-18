package com.example.mohameddhiahachem.bonplanapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.example.mohameddhiahachem.bonplanapp.Entity.Plan;
import com.example.mohameddhiahachem.bonplanapp.Entity.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private Button login, register, forgot;
    private RequestQueue requestQueue;
    private ProgressBar loading;
    private DBHelper db;

    User[] users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loading = (ProgressBar) findViewById(R.id.loading);
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_create_account);
        forgot = (Button) findViewById(R.id.btn_forgot_password);
        db =new DBHelper(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userLogin();
                LogUser();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });


    }

    private void LogUser() {
        String URL_REGIST = "http://10.0.3.2:8000/api/login";
        loading.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        final String username = email.getText().toString().trim();
        final String pass = password.getText().toString().trim();

        if (username.isEmpty()) {
            loading.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            Toast.makeText(this, getResources().getString(R.string.app_empty_email), Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isValidEmail(username)){
            loading.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            Toast.makeText(this, getResources().getString(R.string.app_invalid_email), Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.isEmpty()) {
            loading.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            Toast.makeText(this, getResources().getString(R.string.app_empty_password), Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("code");
                    if(success){
                        loading.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);

                        Gson gson = new Gson();
                        users = gson.fromJson(jsonObject.getJSONArray("user").toString(), User[].class);

                        for( User user : users){

                            if(!user.getRoles().equals("ROLE_USER")){
                                Toast.makeText(Login.this, getResources().getString(R.string.app_not_have_access), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            db.insertUser(new User(user.getId(), user.getEmail(),password.getText().toString() , user.getFirstName(), user.getLastName(), user.getImage(), user.getAddress(), user.getTelephone() ));
                        }

                        Log.d("User db::", db.getUser().toString());
                        startActivity(new Intent(Login.this, Navigation.class));

                    }else{
                        loading.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                        Toast.makeText(Login.this, getResources().getString(R.string.app_invalid_email_or_password), Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){

                    Toast.makeText(Login.this, "Server busy try again", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "something wrong", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", username);
                params.put("password", pass);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                2,
                1f));

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(stringRequest);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
