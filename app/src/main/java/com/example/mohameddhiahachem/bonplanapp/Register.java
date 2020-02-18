package com.example.mohameddhiahachem.bonplanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText email, firstName, lastName, password;
    private Button signUp, login;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.sign_email);
        firstName = (EditText) findViewById(R.id.sign__first_name);
        lastName = (EditText) findViewById(R.id.sign_last_name);
        password = (EditText) findViewById(R.id.sign_password);
        signUp = (Button) findViewById(R.id.btn_sign_up);
        login = (Button) findViewById(R.id.btn_return_login);
        loading = (ProgressBar)findViewById(R.id.loading);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private void RegisterUser() {
        String URL_REGIST = "http://10.0.3.2:8000/api/register";
        loading.setVisibility(View.VISIBLE);
        signUp.setVisibility(View.GONE);
        final String username = email.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        final String first_name = firstName.getText().toString().trim();
        final String last_name = lastName.getText().toString().trim();



        if(username.isEmpty()){
            loading.setVisibility(View.GONE);
            signUp.setVisibility(View.VISIBLE);
            Toast.makeText(this, getResources().getString(R.string.app_empty_email), Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isValidEmail(username)){
            loading.setVisibility(View.GONE);
            signUp.setVisibility(View.VISIBLE);
            Toast.makeText(this, getResources().getString(R.string.app_invalid_email), Toast.LENGTH_SHORT).show();
            return;
        }

        if(first_name.isEmpty()){
            loading.setVisibility(View.GONE);
            signUp.setVisibility(View.VISIBLE);
            Toast.makeText(this, getResources().getString(R.string.app_empty_first_name), Toast.LENGTH_SHORT).show();
            return;
        }

        if(last_name.isEmpty()){
            loading.setVisibility(View.GONE);
            signUp.setVisibility(View.VISIBLE);
            Toast.makeText(this, getResources().getString(R.string.app_empty_last_name), Toast.LENGTH_SHORT).show();
            return;
        }

        if(pass.isEmpty()){
            loading.setVisibility(View.GONE);
            signUp.setVisibility(View.VISIBLE);
            Toast.makeText(this, getResources().getString(R.string.app_empty_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if(pass.length() < 6){
            loading.setVisibility(View.GONE);
            signUp.setVisibility(View.VISIBLE);
            Toast.makeText(this, getResources().getString(R.string.app_password_less_than), Toast.LENGTH_SHORT).show();
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
                        signUp.setVisibility(View.VISIBLE);
                        Toast.makeText(Register.this, "good", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                    }else{
                        loading.setVisibility(View.GONE);
                        signUp.setVisibility(View.VISIBLE);
                        Toast.makeText(Register.this, "Email Used", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){

                    Toast.makeText(Register.this, "Server busy try again", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    signUp.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        signUp.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                params.put("first_name", firstName.getText().toString());
                params.put("last_name", lastName.getText().toString());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                2,
                1f));

        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(stringRequest);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
