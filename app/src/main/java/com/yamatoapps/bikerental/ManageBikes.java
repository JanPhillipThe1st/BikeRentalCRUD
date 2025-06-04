package com.yamatoapps.bikerental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ManageBikes extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListView lvManageBikes;
    Button btnExit;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bikes);

        lvManageBikes = findViewById(R.id.lvManageBikes);
        btnExit = findViewById(R.id.btnExit);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        btnExit.setOnClickListener(view -> {
            finish();
        });
        ArrayList<BikeModel> bikeModels = new ArrayList<BikeModel>();
        BikeAdapter adapter = new BikeAdapter(ManageBikes.this,0,bikeModels);
        db.collection("bikes").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                adapter.add(new BikeModel(
                        snapshot.getString("model"),
                        snapshot.getString("description"),
                        snapshot.getString("age_range"),
                        snapshot.getString("height_range"),
                        snapshot.getString("rate"),
                        snapshot.getString("image_url"),
                        snapshot.getId()
                ));
                lvManageBikes.setAdapter(adapter);
            }
        });
    }
}