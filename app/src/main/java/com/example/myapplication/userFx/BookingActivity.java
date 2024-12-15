package com.example.myapplication.userFx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

public class BookingActivity extends AppCompatActivity {

    private TextView tvPageTitle, tvVenueFloor;
    private ImageView btnBack, venueImage;
    private EditText etDate, etStartTime, etEndTime, etPlace;
    private Button btnBookNow;
    private CardView venueCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize views
        tvPageTitle = findViewById(R.id.tvPageTitle);
        btnBack = findViewById(R.id.btnBack);
        venueImage = findViewById(R.id.venueImage);
        etDate = findViewById(R.id.etDate);
        etStartTime = findViewById(R.id.etStartTime);
        etEndTime = findViewById(R.id.etEndTime);
        btnBookNow = findViewById(R.id.btnBookNow);
        venueCard = findViewById(R.id.venueCard);

        // Get data passed from VenueActivity
        Intent intent = getIntent();
        String venueName = intent.getStringExtra("venueName");
        String venueFloor = intent.getStringExtra("venueFloor");
        String venueImageUrl = intent.getStringExtra("venueImageUrl");

        // Check if the intent data is missing or invalid
        if (venueName == null || venueImageUrl == null) {
            Log.e("BookingActivity", "Missing venue details");
            finish();
            return;
        }

        // Set data into views
        tvPageTitle.setText("Booking - " + venueName);
        etPlace.setText(venueName);

        if (venueFloor != null && !venueFloor.isEmpty()) {
            tvVenueFloor = new TextView(this);
            tvVenueFloor.setText("Floor: " + venueFloor);
            tvVenueFloor.setTextSize(16);
            tvVenueFloor.setTextColor(getResources().getColor(android.R.color.black));
            venueCard.addView(tvVenueFloor);
        }

        Glide.with(this)
                .load(venueImageUrl)
                .placeholder(R.drawable.logo_2)  // Add a placeholder image
                .into(venueImage);

        // Set back button functionality
        btnBack.setOnClickListener(v -> finish());

        // Set up the "Book Now" button click listener
        btnBookNow.setOnClickListener(v -> {
            // Get the values entered by the user in the EditText fields
            String date = etDate.getText().toString();
            String startTime = etStartTime.getText().toString();
            String endTime = etEndTime.getText().toString();
            String place = etPlace.getText().toString();

            // Validate input (if needed)
            if (date.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || place.isEmpty()) {
                Log.e("BookingActivity", "All fields must be filled.");
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Handle booking logic (e.g., save the data to Firebase, send it to a server, etc.)
            Log.d("BookingActivity", "Booking Details - Date: " + date + ", Start Time: " + startTime +
                    ", End Time: " + endTime + ", Place: " + place);
        });
    }
}
