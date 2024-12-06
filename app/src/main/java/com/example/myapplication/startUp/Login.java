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
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.main.MainActivity;
import com.example.myapplication.R;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button loginButton;
    TextView signUp;
    boolean isPasswordVisible = false;
    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

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
                // to edit default
                if(email.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
                    Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                }  else {
                    Toast.makeText(Login.this, "Login Failed.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToSignUp();
            }
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        isPasswordVisible = !isPasswordVisible;
        password.setSelection(password.getText().length());
    }

    private void redirectToSignUp() {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }

    private void handleLogin() {
        String enteredUsername = email.getText().toString();
        String enteredPassword = password.getText().toString();

        if (enteredUsername.equals("user") && enteredPassword.equals("1234")) {
            Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(Login.this, "Login Failed.", Toast.LENGTH_SHORT).show();
        }
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
