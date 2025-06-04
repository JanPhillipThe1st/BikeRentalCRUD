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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BikeAdapter extends ArrayAdapter<BikeModel> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public BikeAdapter(Context context, int resource,  ArrayList<BikeModel> bikeModels) {
        super(context, 0, bikeModels);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        BikeModel item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bike_card_item, parent, false);
        }

        // Lookup view for data population
        TextView tvModel = (TextView) convertView.findViewById(R.id.tvModel);
        TextView tvRate = (TextView) convertView.findViewById(R.id.tvRate);
        TextView tvAgeRange = (TextView) convertView.findViewById(R.id.tvAgeRange);
        TextView tvHeightRange = (TextView) convertView.findViewById(R.id.tvHeightRange);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        ImageView ivBike = (ImageView) convertView.findViewById(R.id.ivBike);


        tvModel.setText(item.model);
        tvRate.setText(item.rate);
        tvAgeRange.setText(item.age_range);
        tvHeightRange.setText(item.height_range);
        tvDescription.setText(item.description);
        Picasso.get().load(item.image_url).into(ivBike);

        Button btnEdit, btnDelete;
        btnEdit = convertView.findViewById(R.id.btnEdit);
        btnDelete = convertView.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(view -> {
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
            alertDialogBuilder.setTitle("Delete Bike");
            alertDialogBuilder.setMessage("Are you sure you want to delete this bike?");
            alertDialogBuilder.setPositiveButton("NO", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            alertDialogBuilder.setNegativeButton("YES", (dialogInterface, i) -> {

                MaterialAlertDialogBuilder deleteDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
                deleteDialogBuilder.setTitle("Delete success");
                deleteDialogBuilder.setMessage("Bike deleted successfully!");
                deleteDialogBuilder.setPositiveButton("OK", (deleteDialogBuilderDialogInterface,j)->{
                    deleteDialogBuilderDialogInterface.dismiss();
                    Activity context = (Activity) parent.getContext();
                });
                db.collection("bikes").document(item.id).delete().addOnSuccessListener(unused -> {
                    deleteDialogBuilder.create().show();
                    dialogInterface.dismiss();
                });
            });
            alertDialogBuilder.create().show();
        });

        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(parent.getContext(), EditBike.class);
            intent.putExtra("document_id",item.id);
            parent.getContext().startActivity(intent);
        });
        return convertView;
    }
}
