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
        databaseReference = FirebaseDatabase.getInstance().getReference("venues");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.u_fragment_booking, container, false);

        Log.e("UserBookingFragment", "Venue");

        // Buttons for venues
        Button buttonDevesse = view.findViewById(R.id.check_availability_button_devesse);
        Button buttonAmphi = view.findViewById(R.id.check_availability_button_amphi);
        Button buttonOval = view.findViewById(R.id.check_availability_button_oval);
        Button buttonLab = view.findViewById(R.id.check_availability_button_lab);

        // Set listeners for each button
        buttonDevesse.setOnClickListener(v -> openBookingActivity("Test Venue", "1st Floor", "test_image_url", true));
        buttonAmphi.setOnClickListener(v -> openBookingActivity("Test Venue", "2nd Floor", "test_image_url", false));
        buttonOval.setOnClickListener(v -> openBookingActivity("Test Venue", "Open Space", "test_image_url", true));
        buttonLab.setOnClickListener(v -> openBookingActivity("Test Venue", "3rd Floor", "test_image_url", true));

        return view;
    }


    private void fetchVenueDetails(String venueId) {
        Log.e("UserBookingFragment", "Database path: " + databaseReference.child(venueId).toString());

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

                    if (venueName != null) {
                        Log.e("UserBookingFragment", "Booking accepted for " + venueName);
                        openBookingActivity(venueName, venueFloor, venueImage, venueAvailability);
                    } else {
                        Log.e("UserBookingFragment", "Venue name is missing");
                        Toast.makeText(getActivity(), "Venue name is missing", Toast.LENGTH_SHORT).show();
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


    private void openBookingActivity(String venueName, String venueFloor, String venueImageUrl, Boolean venueAvailability) {
        Log.e("UserBookingFragment", "Trying to intent");

        // Hardcoded data for testing
        String testVenueName = "Test Venue";
        String testVenueFloor = "1st Floor";
        String testVenueImageUrl = "test_image_url";
        Boolean testVenueAvailability = true;

        Intent intent = new Intent(getActivity(), bookingConfirmation.class);
        intent.putExtra("venueName", testVenueName); // Hardcoded name
        intent.putExtra("venueFloor", testVenueFloor); // Hardcoded floor
        intent.putExtra("venueImageUrl", testVenueImageUrl); // Hardcoded image URL
        intent.putExtra("venueAvailability", testVenueAvailability); // Hardcoded availability
        startActivity(intent);
    }

}
