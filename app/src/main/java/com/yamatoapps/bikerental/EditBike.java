package com.yamatoapps.bikerental;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditBike extends AppCompatActivity {
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    Uri fileUri;
    Button btnSave,btnSeeBikes;
    ImageView ivBike;
    String document_id = "";
    String image_url = "";
    ProgressDialog progressDialog;
    TextView tvModel,tvRate,tvAgeRange,tvHeightRange,tvDescription;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView iv = findViewById(R.id.ivBike);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK && data != null && data != null) {
            fileUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),fileUri);
                iv.setImageBitmap(bitmap);
                uploadImage();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bike);

        tvModel = findViewById(R.id.tvModel);
        tvRate = findViewById(R.id.tvRate);
        tvAgeRange = findViewById(R.id.tvAgeRange);
        tvHeightRange = findViewById(R.id.tvHeightRange);
        tvDescription = findViewById(R.id.tvDescription);
        ivBike = findViewById(R.id.ivBike);
        document_id = getIntent().getStringExtra("document_id");


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.setMessage("Posting your Bike...");

        btnSave = findViewById(R.id.btnSave);
        db.collection("bikes").document(document_id).get().addOnSuccessListener(documentSnapshot -> {
            tvModel.setText(documentSnapshot.getString("model"));
            tvRate.setText(documentSnapshot.getString("rate"));
            tvAgeRange.setText(documentSnapshot.getString("age_range"));
            tvHeightRange.setText(documentSnapshot.getString("height_range"));
            tvDescription.setText(documentSnapshot.getString("description"));
            Picasso.get().load(documentSnapshot.getString("image_url")).into(ivBike);
            image_url = documentSnapshot.getString("image_url");
        });
        btnSave.setOnClickListener(view -> {
            progressDialog.show();
            Map<String, Object> listing = new HashMap<>();
            listing.put("model", tvModel.getText().toString());
            listing.put("rate", tvRate.getText().toString());
            listing.put("age_range", tvAgeRange.getText().toString());
            listing.put("description", tvDescription.getText().toString());
            listing.put("height_range", tvHeightRange.getText().toString());
            listing.put("image_url",  image_url);
            db.collection("bikes").document(document_id).update(listing).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Bike information successfully updated!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        });



        Intent imageIntent = new Intent();
        ivBike.setOnClickListener(view -> {
            imageIntent.setType("image/*");
            imageIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(imageIntent,"Pick image to upload"),22);

        });
    }
    public  void uploadImage(){
        btnSave.setEnabled(false);
        if (fileUri != null){

            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child(UUID.randomUUID().toString());
            UploadTask uploadTask = (UploadTask) storageReference.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {

            }).addOnFailureListener(listener->{
                btnSave.setEnabled(false);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Fail to Upload Image..", Toast.LENGTH_SHORT)
                        .show();
            });
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        btnSave.setEnabled(false);
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        image_url = task.getResult().toString();
                        btnSave.setEnabled(true);
                    } else {
                        btnSave.setEnabled(false);
                        // Handle failures
                        // ...
                    }
                }
            });

        }
    }
}