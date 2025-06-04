package com.yamatoapps.bikerental;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    TextView tvUsername, tvPassword;
    Dialogs dialogs = new Dialogs();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        tvUsername = findViewById(R.id.tvUsername);
        tvPassword = findViewById(R.id.tvPassword);


        btnLogin.setOnClickListener(view ->{
                dialogs.showProgressDialog(MainActivity.this,"Logging in","Login");
            if (tvUsername.getText().toString().contains( "admin") && tvPassword.getText().toString().contains( "admin")){
                dialogs.progressDialog.dismiss();
                dialogs.showMessage(MainActivity.this,"Logged in as admin!","Login success.");
                startActivity(new Intent(MainActivity.this,AddBikeListing.class));
            }
            else if (tvUsername.getText().toString().contains( "user") && tvPassword.getText().toString().contains( "user")){
                dialogs.progressDialog.dismiss();
                dialogs.showMessage(MainActivity.this,"Logged in as user!","Login success.");
                startActivity(new Intent(MainActivity.this,RentalCatalogue.class));
            }
            else{
                dialogs.progressDialog.dismiss();
                dialogs.showMessage(MainActivity.this,"Login failed. No Account found.","Login failed.");
            }
        });
    }
}