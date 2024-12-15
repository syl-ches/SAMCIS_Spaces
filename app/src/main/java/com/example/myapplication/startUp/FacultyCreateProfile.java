package com.example.myapplication.startUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.main.MainActivity;
import com.example.myapplication.userFx.UserHomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FacultyCreateProfile extends AppCompatActivity {

    EditText idNumEditText;
    Spinner deptCategorySpinner;
    Button cancelBtn, saveBtn;
    String userId; // Retrieved from SignUp activity
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_faculty);

        // Initialize Firebase Realtime Database reference
        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        // Bind UI components
        idNumEditText = findViewById(R.id.idNum);
        deptCategorySpinner = findViewById(R.id.deptCategory);
        cancelBtn = findViewById(R.id.cancelBttn);
        saveBtn = findViewById(R.id.saveBttn);

        // Retrieve userId passed from SignUp
        userId = getIntent().getStringExtra("USER_ID");

        setupDepartmentSpinner();

        cancelBtn.setOnClickListener(v -> navigateBack());
        saveBtn.setOnClickListener(v -> saveProfileToDatabase());
    }

    private void setupDepartmentSpinner() {
        // Populate department spinner with options
        String[] departments = {
                "Choose Department",
                "CIS",
                "Math",
                "Accountancy",
                "HTM",
                "Gen. Ed",
                "Non-Teaching"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deptCategorySpinner.setAdapter(adapter);
    }

    private void saveProfileToDatabase() {
        String idNumber = idNumEditText.getText().toString().trim();
        String selectedDept = deptCategorySpinner.getSelectedItem().toString();

        // Validate inputs
        if (idNumber.isEmpty() || selectedDept.equals("Choose Department")) {
            validateFields(idNumber, selectedDept);
            return;
        }

        // Build faculty profile map
        Map<String, Object> facultyProfile = new HashMap<>();
        facultyProfile.put("ID Number", idNumber);
        facultyProfile.put("Department", selectedDept);
        facultyProfile.put("Role", "Faculty");

        // Save to Realtime Database under the specific user ID
        dbRef.child(userId).updateChildren(facultyProfile)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show();
                    navigateToUserHome();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error saving profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void validateFields(String idNumber, String department) {
        if (idNumber.isEmpty()) idNumEditText.setError("This field is required");
        if (department.equals("Choose Department"))
            Toast.makeText(this, "Please select a valid department.", Toast.LENGTH_SHORT).show();
    }

    private void navigateBack() {
        startActivity(new Intent(FacultyCreateProfile.this, SignUp.class));
        finish();
    }

    private void navigateToUserHome() {
        Intent intent = new Intent(FacultyCreateProfile.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
