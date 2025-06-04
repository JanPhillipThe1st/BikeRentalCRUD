package com.yamatoapps.bikerental;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BikeAdapterRental extends ArrayAdapter<BikeModel> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public BikeAdapterRental(Context context, int resource, ArrayList<BikeModel> bikeModels) {
        super(context, 0, bikeModels);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        BikeModel item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bike_card_item_rental, parent, false);
        }

        // Lookup view for data population
        TextView tvModel = (TextView) convertView.findViewById(R.id.tvModel);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        TextView tvAgeRange = (TextView) convertView.findViewById(R.id.tvAgeRange);
        TextView tvHeightRange = (TextView) convertView.findViewById(R.id.tvHeightRange);
        ImageView ivBike = (ImageView) convertView.findViewById(R.id.ivBike);


        tvModel.setText(item.model);
        tvAgeRange.setText(item.age_range);
        tvHeightRange.setText(item.height_range);
        tvDescription.setText(item.description);
        Picasso.get().load(item.image_url).into(ivBike);

        Button btnReserve;
        btnReserve = convertView.findViewById(R.id.btnReserve);

        btnReserve.setOnClickListener(view -> {
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
            alertDialogBuilder.setTitle("Rent Bike");
            alertDialogBuilder.setMessage("Are you sure you want to rent this bike?");
            alertDialogBuilder.setPositiveButton("NO", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            alertDialogBuilder.setNegativeButton("YES", (dialogInterface, i) -> {
                parent.getContext().startActivity(new Intent(parent.getContext(),RentalScreen.class));
                dialogInterface.dismiss();
            });
            alertDialogBuilder.create().show();
        });

        return convertView;
    }
}
