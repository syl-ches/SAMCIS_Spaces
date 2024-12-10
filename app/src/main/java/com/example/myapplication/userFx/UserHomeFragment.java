package com.example.myapplication.userFx;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserHomeFragment extends Fragment {

    // Declare the database reference
    private DatabaseReference rtdbRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.u_fragment_home, container, false);

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://samcis-spaces-d229d-default-rtdb.firebaseio.com/");

        // Get reference to "venues"
        rtdbRef = database.getReference("venues");

        // Set click listeners for the ImageViews
        View image1 = view.findViewById(R.id.image1);
        View image2 = view.findViewById(R.id.image2);
        View image3 = view.findViewById(R.id.image3);

        // Navigate to booking fragment on image click
        image1.setOnClickListener(v -> navigateToBookingFragment());
        image2.setOnClickListener(v -> navigateToBookingFragment());
        image3.setOnClickListener(v -> navigateToBookingFragment());

        // Set click listener for Button 1 to navigate to the booking fragment
        Button button1 = view.findViewById(R.id.button1);
        button1.setOnClickListener(v -> navigateToBookingFragment());

        // Set click listener for Button 2 to navigate to BookingHistoryActivity
        View button2 = view.findViewById(R.id.button2);
        button2.setOnClickListener(v -> navigateToBookingHistoryActivity());

        return view;
    }

    // Method to navigate to the booking fragment
    private void navigateToBookingFragment() {
        Fragment bookingFragment = new UserBookingFragment(); // Assuming you have a BookingFragment class
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, bookingFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Method to navigate to the BookingHistoryActivity
    private void navigateToBookingHistoryActivity() {
        Intent intent = new Intent(getActivity(), BookingHistoryActivity.class);
        startActivity(intent);
    }
}