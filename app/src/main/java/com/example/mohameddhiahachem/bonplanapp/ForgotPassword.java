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

public class ForgotPassword extends AppCompatActivity {

    private EditText email;
    private Button resetPassword, login;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = (EditText) findViewById(R.id.reset_email);
        resetPassword = (Button) findViewById(R.id.btn_reset_password);
        loading = (ProgressBar)findViewById(R.id.loading_forgot_password);
        login = (Button) findViewById(R.id.btn_r_login);


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this, Login.class));
            }
        });
    }

    private void forgotPassword() {
        String URL_REGIST = "http://10.0.3.2:8000/api/request-password-reset";
        loading.setVisibility(View.VISIBLE);
        resetPassword.setVisibility(View.GONE);
        final String username = email.getText().toString().trim();

        if(username.isEmpty()){
            loading.setVisibility(View.GONE);
            resetPassword.setVisibility(View.VISIBLE);
            Toast.makeText(this, getResources().getString(R.string.app_empty_email), Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isValidEmail(username)){
            loading.setVisibility(View.GONE);
            resetPassword.setVisibility(View.VISIBLE);
            Toast.makeText(this, getResources().getString(R.string.app_invalid_email), Toast.LENGTH_SHORT).show();
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("code");
                    if(success){
                        Toast.makeText(ForgotPassword.this, "We send you a message !", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        resetPassword.setVisibility(View.VISIBLE);
                        startActivity(new Intent(ForgotPassword.this, Login.class));
                    }else{
                        loading.setVisibility(View.GONE);
                        resetPassword.setVisibility(View.VISIBLE);
                        Toast.makeText(ForgotPassword.this, "No Email found !", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){

                    Toast.makeText(ForgotPassword.this, "Server busy try again", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    resetPassword.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgotPassword.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        resetPassword.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email.getText().toString());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                2,
                1f));

        RequestQueue requestQueue = Volley.newRequestQueue(ForgotPassword.this);
        requestQueue.add(stringRequest);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
