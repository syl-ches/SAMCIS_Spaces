package com.example.myapplication.startUp;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SignUp extends AppCompatActivity {

    EditText email, password, confirmPassword;
    Spinner chooseCat;
    Button signUpButton;
    boolean isPasswordVisible = false;
    boolean valid = true;
    CheckBox termsCheckBox;

    protected void OnCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.schoolEmail);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPwd);
        chooseCat = findViewById(R.id.chooseCategory);
        termsCheckBox = findViewById(R.id.tNC);

        checkField(email);
        checkField(password);
        checkField(confirmPassword);
        //checkField(chooseCat);

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to edit default
                if(email.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
                    Toast.makeText(SignUp.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                }  else {
                    Toast.makeText(SignUp.this, "Login Failed.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        termsCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTermsAndConditions();
            }
        });

        /*signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToSignUp();
            }
        });*/

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.categories,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        chooseCat.setAdapter(adapter);

        chooseCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (!selectedItem.equals("Choose a Category")) {
                    Toast.makeText(SignUp.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_fill, 0);

        } else {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_fill, 0);
        }
        isPasswordVisible = !isPasswordVisible;
        password.setSelection(password.getText().length());
    }

    public boolean checkField(EditText textField) {
        if(textField.getText().toString().isEmpty()) {
            textField.setError("Invalid.");
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }

    private void showTermsAndConditions() {
        String terms = "Terms and Conditions\n\n" +
                "1. **Introduction**\n" +
                "   Welcome to SAMCIS Spaces. By using our application, you agree to comply with these terms.\n\n" +
                "2. **User Accounts**\n" +
                "   - You must provide accurate information during sign-up.\n" +
                "   - You are responsible for maintaining the confidentiality of your login credentials.\n\n" +
                "3. **User Conduct**\n" +
                "   - No harmful, abusive, or illegal activity is allowed.\n" +
                "   - Respect the privacy and data of other users.\n\n" +
                "4. **Data Privacy**\n" +
                "   Your personal information will be handled according to our Privacy Policy.\n\n" +
                "5. **Limitation of Liability**\n" +
                "   We are not liable for any damages caused by misuse of the app.\n\n" +
                "6. **Termination**\n" +
                "   We may suspend or terminate your access for violating these terms.\n\n" +
                "7. **Contact Us**\n" +
                "   For questions or support, contact: support@samcisspaces.com";

        new AlertDialog.Builder(this)
                .setTitle("Terms and Conditions")
                .setMessage(terms)
                .setPositiveButton("OK", null)
                .show();
    }
}
