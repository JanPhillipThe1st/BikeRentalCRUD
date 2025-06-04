package com.yamatoapps.bikerental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RentalCatalogue extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListView lvBikes;
    Button btnExit;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_catalogue);

        lvBikes = findViewById(R.id.lvBikes);
        btnExit = findViewById(R.id.btnExit);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        btnExit.setOnClickListener(view -> {
            finish();
        });

        ArrayList<BikeModel> bikeModels = new ArrayList<BikeModel>();
        BikeAdapterRental adapter = new BikeAdapterRental(RentalCatalogue.this,0,bikeModels);
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
                lvBikes.setAdapter(adapter);
            }
        });
    }
}