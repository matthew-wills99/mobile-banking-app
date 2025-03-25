package com.example.comp4200project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        boolean staySignedIn = sharedPreferences.getBoolean("StaySignedIn", false);
        String savedPin = sharedPreferences.getString("UserPIN", "");

        if(staySignedIn && !savedPin.isEmpty())
        {
            Intent intent = new Intent(this, PinVerificationActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
