package com.example.mohameddhiahachem.bonplanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mohameddhiahachem.bonplanapp.DB.DBHelper;

public class MainActivity extends AppCompatActivity {

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);


        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(3000);

                    if(db.isExist()){
                        startActivity(new Intent(MainActivity.this, Navigation.class));
                    }else{
                        startActivity(new Intent(MainActivity.this, Login.class));
                    }


                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
