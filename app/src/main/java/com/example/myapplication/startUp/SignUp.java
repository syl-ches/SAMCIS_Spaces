package com.example.myapplication.startUp;

import android.content.Intent;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.main.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText email, name, password, confirmPassword;
    Spinner chooseCat;
    Button signUpButton;
    boolean isPasswordVisible = false;
    boolean valid = true;
    CheckBox termsCheckBox;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.schoolEmail);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPwd);
        chooseCat = findViewById(R.id.chooseCategory);
        termsCheckBox = findViewById(R.id.tNC);
        signUpButton = findViewById(R.id.signUpButton);

        checkField(email);
        checkField(name);
        checkField(password);
        checkField(confirmPassword);
        //checkField(chooseCat);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!termsCheckBox.isChecked()) {
                    Toast.makeText(SignUp.this, "Please accept Terms and Conditions", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (valid) {
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser user = fAuth.getCurrentUser();
                                    DocumentReference df = fStore.collection("Users").document(user.getUid());

                                    Map<String, Object> userInfo = new HashMap<>();
                                    userInfo.put("UserEmail", email.getText().toString());
                                    userInfo.put("Fullname", name.getText().toString());
                                    userInfo.put("isAdmin", "0");

                                    df.set(userInfo);

                                    Toast.makeText(SignUp.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUp.this, MainActivity.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUp.this, "Failed to create account: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

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

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
