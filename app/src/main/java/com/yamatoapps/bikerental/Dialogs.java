package com.yamatoapps.bikerental;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Dialogs{
    public  ProgressDialog progressDialog;
    public  MaterialAlertDialogBuilder alertDialogBuilder;
    public void showProgressDialog(Context context, String message, String title){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();

    }
    public void showMessage(Context context, String message, String title){
        alertDialogBuilder = new MaterialAlertDialogBuilder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setPositiveButton("OK",(dialogInterface, i) -> {
            dialogInterface.dismiss();
        } );
        alertDialogBuilder.create().show();
    }
}
