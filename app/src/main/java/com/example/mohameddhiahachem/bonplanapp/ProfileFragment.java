package com.example.mohameddhiahachem.bonplanapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.mohameddhiahachem.bonplanapp.Entity.User;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {


    private TextView name, email, address, telephone;
    private Button btnLogout, btnUpdate;
    private DBHelper db;
    private CircleImageView profileImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        db = new DBHelper(getContext());
        name = (TextView) view.findViewById(R.id.profile_name);
        email = (TextView) view.findViewById(R.id.profile_email);
        address = (TextView) view.findViewById(R.id.profile_address);
        telephone = (TextView) view.findViewById(R.id.profile_telephone);
        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        btnLogout = (Button) view.findViewById(R.id.profile_btn_logout);
        btnUpdate = (Button) view.findViewById(R.id.profile_btn_update);

        // set User DATA
        User user = db.getUser();
        name.setText(user.getFirstName() + " " + user.getLastName());
        email.setText(user.getEmail());

        if(! (user.getImage() == null)){
            Picasso.get().load("http://10.0.3.2:8000/uploads/users/" + user.getImage()).resize(100, 100).into(profileImage);
        }

        if(user.getAddress() == null){
            address.setText("Not Address Found");
        }else {
            address.setText(user.getAddress());
        }

        if(user.getTelephone() == null){
            telephone.setText("Not Phone Found");
        }else {
            telephone.setText(user.getTelephone());
        }


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete();
                startActivity(new Intent(getContext(), Login.class));
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowUpdate();
            }
        });

        return view;
    }




    private void ShowUpdate() {

        final AlertDialog.Builder mB = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.activity_update_profile, null);

        Button submit = (Button) view.findViewById(R.id.update_profile_submit);
        mB.setView(view);
        final AlertDialog dialog = mB.create();
        dialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




    }


}
