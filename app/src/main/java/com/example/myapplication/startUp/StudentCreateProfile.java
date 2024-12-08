package com.example.myapplication.startUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class StudentCreateProfile extends AppCompatActivity {

    EditText idNumEditText, yearLevelEditText;
    Spinner programSpinner;
    Button cancelBtn, saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student);

        idNumEditText = findViewById(R.id.idNum);
        yearLevelEditText = findViewById(R.id.yearLevel);
        programSpinner = findViewById(R.id.programCategory);
        cancelBtn = findViewById(R.id.cancelBttn);
        saveBtn = findViewById(R.id.saveBttn);

        String[] programs = {
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
                this, android.R.layout.simple_spinner_dropdown_item, programs);
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

                if (idNumber.isEmpty() || fullName.isEmpty()) {
                    idNumEditText.setError("This field is required");
                    yearLevelEditText.setError("This field is required");
                } else {
                    saveProfile(idNumber, fullName, selectedProgram);
                }
            }
        });
    }

    private void saveProfile(String idNumber, String fullName, String program) {
        String message = "Profile Saved:\nID: " + idNumber +
                "\nName: " + fullName +
                "\nProgram: " + program;
        System.out.println(message);
    }
}
