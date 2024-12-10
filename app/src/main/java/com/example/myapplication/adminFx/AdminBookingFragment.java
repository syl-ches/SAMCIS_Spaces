package com.example.myapplication.adminFx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import com.example.myapplication.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class AdminBookingFragment extends Fragment {

    private CardView cardDevesee, cardAmphitheater, cardOval;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.a_fragment_booking, container, false);

        // Initialize CardViews
        cardDevesee = rootView.findViewById(R.id.card_devesee);
        cardAmphitheater = rootView.findViewById(R.id.card_amphitheater);
        cardOval = rootView.findViewById(R.id.card_oval);

        // Set click listeners
        cardDevesee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Devesee Hall details or another activity
                Intent intent = new Intent(getActivity(), DeveseeHallActivity.class);
                startActivity(intent);
            }
        });

        cardAmphitheater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Amphitheater details or another activity
                Intent intent = new Intent(getActivity(), AmphitheaterActivity.class);
                startActivity(intent);
            }
        });
        cardOval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OvalActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
