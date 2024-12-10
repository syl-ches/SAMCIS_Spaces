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
        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("venues");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.u_fragment_booking, container, false);

        // Buttons for each venue
        Button buttonDevesse = view.findViewById(R.id.check_availability_button_devesse);
        Button buttonAmphi = view.findViewById(R.id.check_availability_button_amphi);
        Button buttonOval = view.findViewById(R.id.check_availability_button_oval);
        Button buttonLab = view.findViewById(R.id.check_availability_button_lab);

        // Set OnClickListeners for each button
        buttonDevesse.setOnClickListener(v -> openBookingActivity("devesse"));
        buttonAmphi.setOnClickListener(v -> openBookingActivity("amphitheater"));
        buttonOval.setOnClickListener(v -> openBookingActivity("desmedt_oval"));
        buttonLab.setOnClickListener(v -> openBookingActivity("comp_lab"));

        return view; // Return the inflated view
    }

    private void openBookingActivity(String venueId) {
        if (getActivity() == null) {
            return;
        }

        // Log the venueId being fetched
        Log.d("User BookingFragment", "Fetching data for venue: " + venueId);

        databaseReference.child(venueId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    // Log the fetched data
                    Log.d("User BookingFragment", "Venue data found: " + snapshot.toString());
                    // ... (rest of your code)
                } else {
                    Log.d("User BookingFragment", "Venue data not found");
                    Toast.makeText(getActivity(), "Venue data not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("User BookingFragment", "Failed to fetch venue data", task.getException());
                Toast.makeText(getActivity(), "Failed to fetch venue data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}