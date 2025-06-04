package com.yamatoapps.bikerental;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;

public class RentalScreen extends AppCompatActivity {
    Button btnRentFrom,btnRentTo,btnRentNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_screen);

        final Calendar rentFromCalendar = Calendar.getInstance();
        final Calendar rentUntilCalendar = Calendar.getInstance();
         btnRentFrom = findViewById(R.id.btnRentFrom);
        btnRentNow = findViewById(R.id.btnRentNow);
        btnRentTo = findViewById(R.id.btnRentTo);

        btnRentNow.setOnClickListener(view -> {

            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(RentalScreen.this);
            alertDialogBuilder.setTitle("Rent Bike");
            alertDialogBuilder.setMessage("Are you sure you want to rent this bike?");
            alertDialogBuilder.setPositiveButton("NO", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            alertDialogBuilder.setNegativeButton("YES", (dialogInterface, i) -> {

                MaterialAlertDialogBuilder alertDialogBuilder1 = new MaterialAlertDialogBuilder(RentalScreen.this);
                alertDialogBuilder1.setTitle("Rent Bike");
                alertDialogBuilder1.setMessage("Rental successfully booked!");
                alertDialogBuilder.setPositiveButton("OK", (dialogInterface1, i1) -> {
                    finish();
                    dialogInterface1.dismiss();
                });
                alertDialogBuilder1.create().show();
                dialogInterface.dismiss();
            });
            alertDialogBuilder.create().show();
        });
        btnRentFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.

                // on below line we are getting
                // our day, month and year.
                int month1,date1,year1;
                month1 = rentFromCalendar.get(Calendar.MONTH);
                date1 = rentFromCalendar.get(Calendar.DAY_OF_MONTH);
                year1 = rentFromCalendar.get(Calendar.YEAR);
                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        RentalScreen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                rentFromCalendar.set(Calendar.DATE,dayOfMonth);
                                rentFromCalendar.set(Calendar.MONTH,monthOfYear);
                                rentFromCalendar.set(Calendar.YEAR,year);
                                TimePickerDialog timePickerDialog = new TimePickerDialog(
                                        // on below line we are passing context.
                                        RentalScreen.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                                rentFromCalendar.set(Calendar.HOUR_OF_DAY,i);
                                                rentFromCalendar.set(Calendar.MINUTE,i1);
                                                rentFromCalendar.set(Calendar.SECOND,0);
                                                rentFromCalendar.set(Calendar.MILLISECOND,0);
                                                btnRentFrom.setText(rentFromCalendar.getTime().toLocaleString());
                                            }
                                        },0,0,true);
                                // at last we are calling show to
                                // display our date picker dialog.
                                timePickerDialog.show();
                            }
                        },year1,month1,date1);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        btnRentTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.

                // on below line we are getting
                // our day, month and year.
                int month1,date1,year1;
                month1 = rentUntilCalendar.get(Calendar.MONTH);
                date1 = rentUntilCalendar.get(Calendar.DAY_OF_MONTH);
                year1 = rentUntilCalendar.get(Calendar.YEAR);
                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        RentalScreen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                rentUntilCalendar.set(Calendar.DATE,dayOfMonth);
                                rentUntilCalendar.set(Calendar.MONTH,monthOfYear);
                                rentUntilCalendar.set(Calendar.YEAR,year);
                                TimePickerDialog timePickerDialog = new TimePickerDialog(
                                        // on below line we are passing context.
                                        RentalScreen.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                                rentUntilCalendar.set(Calendar.HOUR_OF_DAY,i);
                                                rentUntilCalendar.set(Calendar.MINUTE,i1);
                                                rentUntilCalendar.set(Calendar.SECOND,0);
                                                rentUntilCalendar.set(Calendar.MILLISECOND,0);
                                                btnRentTo.setText(rentUntilCalendar.getTime().toLocaleString());
                                            }
                                        },0,0,true);
                                // at last we are calling show to
                                // display our date picker dialog.
                                timePickerDialog.show();
                            }
                        },year1,month1,date1);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
    }
}