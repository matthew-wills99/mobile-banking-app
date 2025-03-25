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

public class ForgotPinActivity extends AppCompatActivity {

    private TextInputLayout newPinInputLayout;
    private TextInputEditText newPinInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);

        newPinInputLayout = findViewById(R.id.newPinInputLayout);
        newPinInput = findViewById(R.id.newPinInput);
        Button resetPinButton = findViewById(R.id.resetPinButton);

        resetPinButton.setOnClickListener(v -> resetPin());
    }

    private void resetPin() {
        String newPin = Objects.requireNonNull(newPinInput.getText()).toString().trim();

        if (newPin.length() != 4) {
            newPinInputLayout.setError("Please enter a 4-digit PIN");
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserPIN", newPin);
        editor.apply();

        Toast.makeText(this, "PIN reset successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PinVerificationActivity.class);
        startActivity(intent);
        finish();
    }
}