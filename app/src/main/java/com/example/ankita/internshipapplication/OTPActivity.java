package com.example.ankita.internshipapplication;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

public class OTPActivity extends AppCompatActivity {

    private Pinview pin_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("user_login", MODE_PRIVATE);
        if(prefs.getBoolean("isLogin", false))
        {
            startActivity(new Intent(OTPActivity.this, MainActivity.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        pin_view = (Pinview) findViewById(R.id.pin_view);
        pin_view.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(final Pinview pinview, boolean fromUser) {
                if(pinview.getValue().equals("1234")||pinview.getValue().equals("1235")) {
                    Intent intent = new Intent(OTPActivity.this, LoginActivity.class);
                    intent.putExtra("pinview", pinview.getValue());
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(OTPActivity.this,"Incorrect pin", Toast.LENGTH_LONG).show();
            }
        });


    }
}

