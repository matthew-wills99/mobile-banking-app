package com.example.comp4200project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class PinVerificationActivity extends AppCompatActivity {

    private TextInputLayout pinInputLayout;
    private TextInputEditText pinInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_verification);

        pinInputLayout = findViewById(R.id.pinInputLayout);
        pinInput = findViewById(R.id.pinInput);
        Button verifyButton = findViewById(R.id.verifyButton);
        Button signOutButton = findViewById(R.id.signOutButton);

        verifyButton.setOnClickListener(v -> verifyPin());
        signOutButton.setOnClickListener(v -> handleSignOut());
    }

    private void verifyPin() {
        String enteredPin = Objects.requireNonNull(pinInput.getText()).toString().trim();

        if (enteredPin.isEmpty()) {
            pinInputLayout.setError("PIN is required");
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String savedPin = sharedPreferences.getString("UserPIN", "");

        if (enteredPin.equals(savedPin)) {
            navigateToMainActivity();
        } else {
            pinInputLayout.setError("Incorrect PIN");
        }
    }
    private void handleSignOut() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}