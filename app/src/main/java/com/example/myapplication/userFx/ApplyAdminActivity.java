package com.example.myapplication.userFx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adminFx.AdminHomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ApplyAdminActivity extends AppCompatActivity {

    EditText code;
    Button cancelBttn, applyBttn;
    FirebaseAuth auth;
    FirebaseFirestore db;
    final String VERIFICATION_CODE = "abcd1234";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.u_activity_admin_application);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        code = findViewById(R.id.verifCode);
        cancelBttn = findViewById(R.id.cancel);
        applyBttn = findViewById(R.id.apply);

        cancelBttn.setOnClickListener(v -> redirectToUserProfile());
        applyBttn.setOnClickListener(v -> validateAndApply());
    }

    private void validateAndApply() {
        String enteredCode = code.getText().toString().trim();

        if (enteredCode.isEmpty()) {
            code.setError("Please enter the verification code");
            return;
        }

        if (enteredCode.equals(VERIFICATION_CODE)) {
            updateUserRoleInFirebase();
        } else {
            Toast.makeText(this, "Invalid verification code!", Toast.LENGTH_SHORT).show();
        }
    }

    private void redirectToUserProfile() {
        Intent intent = new Intent(ApplyAdminActivity.this, UserProfileFragment.class);
        startActivity(intent);
        finish();
    }

    private void updateUserRoleInFirebase() {
        String userId = auth.getCurrentUser().getUid();

        Map<String, Object> updates = new HashMap<>();
        updates.put("role", "admin");

        db.collection("Users").document(userId)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "You are now an admin!", Toast.LENGTH_SHORT).show();
                    redirectToAdminHome();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void redirectToAdminHome() {
        Intent intent = new Intent(ApplyAdminActivity.this, AdminHomeFragment.class);
        startActivity(intent);
        finish();
    }
}
