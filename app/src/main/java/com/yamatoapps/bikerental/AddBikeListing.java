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
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddBikeListing extends AppCompatActivity {
    Uri fileUri;
    Button btnSave,btnSeeBikes;
    ImageView ivBike;
    TextView tvModel,tvRate,tvAgeRange,tvHeightRange,tvDescription;
      Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike_listing);

        tvModel = findViewById(R.id.tvModel);
        tvRate = findViewById(R.id.tvRate);
        tvAgeRange = findViewById(R.id.tvAgeRange);
        tvHeightRange = findViewById(R.id.tvHeightRange);
        tvDescription = findViewById(R.id.tvDescription);
        ivBike = findViewById(R.id.ivBike);

       toolbar = findViewById(R.id.toolbar);
            toolbar.setNavigationOnClickListener(view -> {
                finish();
            });
        btnSave = findViewById(R.id.btnSave);
        btnSeeBikes = findViewById(R.id.btnSeeBikes);
        btnSave.setOnClickListener(view -> {
            uploadImage();
        });

        Intent imageIntent = new Intent();
        ivBike.setOnClickListener(view -> {
            imageIntent.setType("image/*");
            imageIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(imageIntent,"Pick image to upload"),22);

        });
        btnSeeBikes.setOnClickListener(view -> {
            startActivity(new Intent(AddBikeListing.this, ManageBikes.class));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView iv = findViewById(R.id.ivBike);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK && data != null && data != null) {
            fileUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),fileUri);
                iv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public  void uploadImage(){
        if (fileUri != null){
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setMessage("Posting your Bike...");
            progressDialog.show();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child(UUID.randomUUID().toString());
            UploadTask uploadTask = (UploadTask) storageReference.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {
            }).addOnFailureListener(listener->{
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Fail to Upload Image..", Toast.LENGTH_SHORT)
                        .show();
            });
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> listing = new HashMap<>();
                        listing.put("model", tvModel.getText().toString());
                        listing.put("rate", tvRate.getText().toString());
                        listing.put("age_range", tvAgeRange.getText().toString());
                        listing.put("description", tvDescription.getText().toString());
                        listing.put("height_range", tvHeightRange.getText().toString());
                        listing.put("image_url",  task.getResult());
                        db.collection("bikes").add(listing).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Congratulations! This bike is now ready to be booked!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });

        }
    }

}