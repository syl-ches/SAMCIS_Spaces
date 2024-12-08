package com.example.myapplication.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.adminFx.AdminBookingFragment;
import com.example.myapplication.adminFx.AdminHomeFragment;
import com.example.myapplication.adminFx.AdminProfileFragment;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.userFx.UserBookingFragment;
import com.example.myapplication.userFx.UserChatboxFragment;
import com.example.myapplication.userFx.UserHomeFragment;
import com.example.myapplication.userFx.UserProfileFragment;
import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new UserHomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuItemId = item.getItemId();

                boolean isAdmin = checkIfAdmin();

                if (menuItemId == R.id.home) {
                    if (isAdmin) {
                        replaceFragment(new AdminHomeFragment());
                    } else {
                        replaceFragment(new UserHomeFragment());
                    }
                    return true;
                }

                else if (menuItemId == R.id.booking) {
                    if (isAdmin) {
                        replaceFragment(new AdminBookingFragment());
                    } else {
                        replaceFragment(new UserBookingFragment());
                    }
                    return true;
                }

                else if (menuItemId == R.id.chatbox) {
                    if (isAdmin) {
                        replaceFragment(new AdminBookingFragment());
                    } else {
                        replaceFragment(new UserChatboxFragment());
                    }
                    return true;
                }

                else if (menuItemId == R.id.profile) {
                    if (isAdmin) {
                        replaceFragment(new AdminProfileFragment());
                    } else {
                        replaceFragment(new UserProfileFragment());
                    }
                    return true;
                }
                return true;
            }
        });
    }

    private boolean checkIfAdmin() {
        return getIntent().getBooleanExtra("isAdmin", false);
    }

    private  void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}