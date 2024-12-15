package com.example.myapplication.startUp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.main.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class StudentCreateProfile extends AppCompatActivity {

    EditText idNumEditText, fullNameEditText;
    Spinner programSpinner;
    Button cancelBtn, saveBtn;

    DatabaseReference dbRef; // Realtime Database reference
    String userId = "example_user_id"; // Replace with actual user ID logic (e.g., FirebaseAuth)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student);

        // Initialize Realtime Database reference
        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        idNumEditText = findViewById(R.id.idNum);
        fullNameEditText = findViewById(R.id.yearLevel);
        programSpinner = findViewById(R.id.programCategory);
        cancelBtn = findViewById(R.id.cancelBttn);
        saveBtn = findViewById(R.id.saveBttn);

        // Populate program spinner
        String[] programs = {
                "Choose Program",
                "BS in Accountancy",
                "BS in Management Accounting",
                "BS in Business Administration major in Financial Management with specialization Business Analytics",
                "BS in Business Administration major in Marketing Management with specialization in Business Analytics",
                "BS in Entrepreneurship with specialization in Business Analytics",
                "BS in Tourism Management",
                "BS in Hospitality Management",
                "BS in Computer Science",
                "BS in Information Technology",
                "BS in Multimedia Arts"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, programs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programSpinner.setAdapter(adapter);

        // Cancel button logic
        cancelBtn.setOnClickListener(v -> navigateBack());

        // Save button logic
        saveBtn.setOnClickListener(v -> saveProfileToDatabase());
    }

    private void saveProfileToDatabase() {
        String idNumber = idNumEditText.getText().toString().trim();
        String fullName = fullNameEditText.getText().toString().trim();
        String selectedProgram = programSpinner.getSelectedItem().toString();

        // Validate inputs
        if (!validateFields(idNumber, fullName, selectedProgram)) {
            return;
        }

        // Build user profile map
        Map<String, Object> studentProfile = new HashMap<>();
        studentProfile.put("ID Number", idNumber);
        studentProfile.put("Full Name", fullName);
        studentProfile.put("Program", selectedProgram);
        studentProfile.put("Role", "Student");

        // Save to Realtime Database under the specific user ID
        dbRef.child(userId).updateChildren(studentProfile)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show();
                    navigateToUserHome();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error saving profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private boolean validateFields(String idNumber, String fullName, String program) {
        boolean isValid = true;

        if (idNumber.isEmpty()) {
            idNumEditText.setError("This field is required");
            isValid = false;
        }
        if (fullName.isEmpty()) {
            fullNameEditText.setError("This field is required");
            isValid = false;
        }
        if (program.equals("Choose Program")) {
            Toast.makeText(this, "Please select a valid program.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void navigateBack() {
        startActivity(new Intent(StudentCreateProfile.this, SignUp.class));
        finish();
    }

    private void navigateToUserHome() {
        Intent intent = new Intent(StudentCreateProfile.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
