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

public class SetPinActivity extends AppCompatActivity {

    private TextInputLayout pinInputLayout;
    private TextInputEditText pinInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        pinInputLayout = findViewById(R.id.pinInputLayout);
        pinInput = findViewById(R.id.pinInput);
        Button setPinButton = findViewById(R.id.setPinButton);

        setPinButton.setOnClickListener(v -> setPin());
    }

    private void setPin() {
        String pin = Objects.requireNonNull(pinInput.getText()).toString().trim();

        if (pin.isEmpty()) {
            pinInputLayout.setError("PIN is required");
            return;
        }

        if (pin.length() != 4) {
            pinInputLayout.setError("PIN must be 4 digits");
            return;
        }

        savePin(pin);

        navigateToMainActivity();
    }

    private void savePin(String pin) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserPIN", pin);
        editor.putBoolean("StaySignedIn", true);
        editor.apply();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}