package com.example.myapplication.userFx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserBookingFragment extends Fragment {

    private DatabaseReference databaseReference;

    public UserBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("venues");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.u_fragment_booking, container, false);

        Log.e("UserBookingFragment", "Venue");

        // Buttons for venues
        Button buttonDevesse = view.findViewById(R.id.check_availability_button_devesse);
        Button buttonAmphi = view.findViewById(R.id.check_availability_button_amphi);
        Button buttonOval = view.findViewById(R.id.check_availability_button_oval);
        Button buttonLab = view.findViewById(R.id.check_availability_button_lab);

        // Set listeners for each button
        buttonDevesse.setOnClickListener(v -> fetchVenueDetails("devesse"));
        buttonAmphi.setOnClickListener(v -> fetchVenueDetails("amphitheater"));
        buttonOval.setOnClickListener(v -> fetchVenueDetails("desmedt_oval"));
        buttonLab.setOnClickListener(v -> fetchVenueDetails("comp_lab"));

        return view;
    }

    private void fetchVenueDetails(String venueId) {
        Log.e("UserBookingFragment", "Database path: " + databaseReference.child(venueId).toString());

        // Fetch venue details from Firebase Realtime Database
        databaseReference.child(venueId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.e("UserBookingFragment", "Fetch Successful");
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    Log.e("UserBookingFragment", "Snapshot value: " + snapshot.getValue());

                    // Fetch data with correct field names
                    String venueName = snapshot.child("name").getValue(String.class);
                    String venueFloor = snapshot.child("floor").getValue(String.class);
                    String venueImage = snapshot.child("image").getValue(String.class); // Corrected field name
                    Boolean venueAvailability = snapshot.child("available").getValue(Boolean.class);

                    if (venueName != null && venueFloor != null) {
                        // Pass dynamic data to the booking activity
                        openBookingActivity(venueName, venueFloor, venueImage, venueAvailability);
                    } else {
                        Log.e("UserBookingFragment", "Venue details are incomplete");
                        Toast.makeText(getActivity(), "Venue details are incomplete", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("UserBookingFragment", "No details found for " + venueId);
                    Toast.makeText(getActivity(), "Venue details not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("UserBookingFragment", "Task failed: " + task.getException());
                Toast.makeText(getActivity(), "Failed to load venue details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Open the booking activity with fetched data
    private void openBookingActivity(String venueName, String venueFloor, String venueImageUrl, Boolean venueAvailability) {
        Log.e("UserBookingFragment", "Trying to open Booking Activity");

        Intent intent = new Intent(getActivity(), bookingConfirmation.class);

        // Pass dynamic data to the activity
        intent.putExtra("venueName", venueName);
        intent.putExtra("venueFloor", venueFloor);
        intent.putExtra("venueImageUrl", venueImageUrl);
        intent.putExtra("venueAvailability", venueAvailability);

        // Start the activity
        startActivity(intent);
    }
}
