package com.example.myapplication.startUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.adminFx.AdminHomeFragment;
import com.example.myapplication.main.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.userFx.UserHomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button loginButton;
    TextView signUp;
    boolean isPasswordVisible = false;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUp);

        checkField(email);
        checkField(password);

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_slash, 0);
        } else {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_fill, 0);
        }
        isPasswordVisible = !isPasswordVisible;
        password.setSelection(password.getText().length());
    }

    private void handleLogin() {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        if (userEmail.isEmpty()) {
            email.setError("Email is required.");
            email.requestFocus();
            return;
        }
        if (userPassword.isEmpty()) {
            password.setError("Password is required.");
            password.requestFocus();
            return;
        }

        fAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    DocumentReference userDocRef = fStore.collection("Users").document(user.getUid());
                    userDocRef.get().addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String role = documentSnapshot.getString("role");
                            if ("admin".equals(role)) {
                                // Redirect to AdminHomeFragment
                                startActivity(new Intent(Login.this, AdminHomeFragment.class));
                            } else {
                                // Redirect to UserHomeFragment
                                startActivity(new Intent(Login.this, UserHomeFragment.class));
                            }
                            finish();
                        } else {
                            Toast.makeText(Login.this, "No role assigned to user.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(Login.this, "Failed to fetch role: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                } else {
                    Toast.makeText(Login.this, "Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
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
}
