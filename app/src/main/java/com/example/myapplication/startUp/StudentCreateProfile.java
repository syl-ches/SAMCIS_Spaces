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
import com.example.myapplication.userFx.UserHomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudentCreateProfile extends AppCompatActivity {

    EditText idNumEditText, yearLevelEditText;
    Spinner programSpinner;
    Button cancelBtn, saveBtn;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student);

        db = FirebaseFirestore.getInstance();

        idNumEditText = findViewById(R.id.idNum);
        yearLevelEditText = findViewById(R.id.yearLevel);
        programSpinner = findViewById(R.id.programCategory);
        cancelBtn = findViewById(R.id.cancelBttn);
        saveBtn = findViewById(R.id.saveBttn);

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

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentCreateProfile.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idNumber = idNumEditText.getText().toString().trim();
                String fullName = yearLevelEditText.getText().toString().trim();
                String selectedProgram = programSpinner.getSelectedItem().toString();

                if (idNumber.isEmpty() || fullName.isEmpty() || selectedProgram.equals("Choose Program")) {
                    if (idNumber.isEmpty()) idNumEditText.setError("This field is required");
                    if (fullName.isEmpty()) yearLevelEditText.setError("This field is required");
                    if (selectedProgram.equals("Choose Program"))
                        Toast.makeText(StudentCreateProfile.this, "Please choose a program.", Toast.LENGTH_SHORT).show();
                } else {
                    saveProfileToFirestore(idNumber, fullName, selectedProgram);
                }
            }
        });
    }

    private void saveProfileToFirestore(String idNumber, String fullName, String program) {
        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("ID Number", idNumber);
        userProfile.put("Full Name", fullName);
        userProfile.put("Program", program);

        db.collection("Profiles")
                .add(userProfile)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(StudentCreateProfile.this, "Profile Saved Successfully!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(StudentCreateProfile.this, UserHomeFragment.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StudentCreateProfile.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
