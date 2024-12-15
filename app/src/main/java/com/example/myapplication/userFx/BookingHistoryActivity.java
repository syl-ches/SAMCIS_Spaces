package com.example.myapplication.userFx;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {

    private RecyclerView todayRecyclerView;
    private RecyclerView nextWeekRecyclerView;
    private RecyclerView pastRecyclerView;

    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        // Initialize Firebase Auth and Database
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        // Initialize views
        todayRecyclerView = findViewById(R.id.todayRecyclerView);
        nextWeekRecyclerView = findViewById(R.id.nextWeekRecyclerView);
        pastRecyclerView = findViewById(R.id.recyclerView);

        // Set up RecyclerViews
        setupRecyclerView(todayRecyclerView);
        setupRecyclerView(nextWeekRecyclerView);
        setupRecyclerView(pastRecyclerView);

        // Load data from Firebase
        loadBookingsData();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadBookingsData() {
        databaseReference.child("bookings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> todayBookings = new ArrayList<>();
                List<String> nextWeekBookings = new ArrayList<>();
                List<String> pastBookings = new ArrayList<>();

                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                    String bookingDate = bookingSnapshot.child("date").getValue(String.class);
                    String bookingDetails = bookingSnapshot.child("details").getValue(String.class);

                    if (bookingDate != null && bookingDetails != null) {
                        if (isToday(bookingDate)) {
                            todayBookings.add(bookingDetails);
                        } else if (isNextWeek(bookingDate)) {
                            nextWeekBookings.add(bookingDetails);
                        } else {
                            pastBookings.add(bookingDetails);
                        }
                    }
                }

                // Set data to adapters (implement your adapters accordingly)
                todayRecyclerView.setAdapter(new BookingAdapter(todayBookings));
                nextWeekRecyclerView.setAdapter(new BookingAdapter(nextWeekBookings));
                pastRecyclerView.setAdapter(new BookingAdapter(pastBookings));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BookingHistoryActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("BookingHistoryActivity", "DatabaseError: " + error.getMessage());
            }
        });
    }

    private boolean isToday(String date) {
        // Implement logic to check if the date is today
        return false;
    }

    private boolean isNextWeek(String date) {
        // Implement logic to check if the date is in the next week
        return false;
    }
}