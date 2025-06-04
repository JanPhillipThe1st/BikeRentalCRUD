package com.yamatoapps.bikerental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SplashScreen extends AppCompatActivity {
    Button btnRentNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        btnRentNow = findViewById(R.id.btnRentNow);
        btnRentNow.setOnClickListener(view ->{
            startActivity(new Intent(SplashScreen.this,MainActivity.class));
        });
    }
}