package com.example.myapplication.userFx;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;

public class UserHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout
        return inflater.inflate(R.layout.u_fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the buttons using their IDs
        ImageButton button1 = view.findViewById(R.id.button1);
        ImageButton button2 = view.findViewById(R.id.button2);

        // Set click listener for Button 1 to navigate to UserBookingFragment
        button1.setOnClickListener(v -> {
            // Replace the current fragment with UserBookingFragment
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, new UserBookingFragment());
            transaction.addToBackStack(null); // Optional: Add to back stack to enable navigation back
            transaction.commit();
        });

        // Set click listener for Button 2 to navigate to ClockActivity
        button2.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), BookingHistoryActivity.class);
            startActivity(intent);
        });
    }
}
