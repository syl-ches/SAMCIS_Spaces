package com.example.myapplication.userFx;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class bookingConfirmation extends AppCompatActivity {

    private Button bookNowButton;
    private FirebaseFirestore db;
    private Dialog popupDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        db = FirebaseFirestore.getInstance();

        bookNowButton = findViewById(R.id.bookBttn);

        bookNowButton.setOnClickListener(view -> showBookingPopup("amphitheater"));
    }

    private void showBookingPopup(String venueId) {
        popupDialog = new Dialog(this);
        popupDialog.setContentView(R.layout.popup_booking_confirmation);
        popupDialog.setCancelable(false); // Prevent dialog dismissal on outside touc

        TextView popupVenueName = popupDialog.findViewById(R.id.popupVenueName);
        TextView popupBookingDate = popupDialog.findViewById(R.id.popupBookingDate);
        TextView popupBookingTime = popupDialog.findViewById(R.id.popupBookingTime);
        Button confirmButton = popupDialog.findViewById(R.id.confirmButton);

        popupVenueName.setText("Venue: Loading...");
        popupBookingDate.setText("Date: Loading...");
        popupBookingTime.setText("Time: Loading...");

        DocumentReference venueRef = db.collection("venues").document(venueId);
        venueRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String venueName = documentSnapshot.getString("name");
                String floor = documentSnapshot.getString("floor");
                Boolean available = documentSnapshot.getBoolean("available");

                popupVenueName.setText("Venue: " + venueName);
                popupBookingDate.setText("Floor: " + floor);
                popupBookingTime.setText("Available: " + (available != null && available ? "Yes" : "No"));
            } else {
                Toast.makeText(this, "Venue data not found!", Toast.LENGTH_SHORT).show();
            }
        });

        confirmButton.setOnClickListener(view -> {
            Toast.makeText(this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
            popupDialog.dismiss();
        });

        popupDialog.show();
    }
}