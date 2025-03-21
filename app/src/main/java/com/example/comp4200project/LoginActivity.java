package com.example.comp4200project;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

public class LoginActivity extends AppCompatActivity {

    private ViewPager2 loginPager;
    private ImageView emailIcon, cardIcon;

    private String email, password, accountNumber, expiryMonth, expiryYear, cardCVV, cardPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginPager = findViewById(R.id.loginPager);
        emailIcon = findViewById(R.id.emailIcon);
        cardIcon = findViewById(R.id.cardIcon);

        updateIcons(0);

        int horizontalMargin = getResources().getDimensionPixelSize(R.dimen.loginpager_margin);
        loginPager.addItemDecoration(new HorizontalMarginItemDecoration(horizontalMargin));

        LoginPagerAdapter adapter = new LoginPagerAdapter(this);
        loginPager.setAdapter(adapter);

        loginPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position)
            {
                updateIcons(position);
            }
        });

        // Email Login
        loginViewModel.getEmail().observe(this, s -> email = s);
        loginViewModel.getPassword().observe(this, s -> password = s);

        // Card Login
        loginViewModel.getAccountNumber().observe(this, s -> accountNumber = s);
        loginViewModel.getExpiryMonth().observe(this, s -> expiryMonth = s);
        loginViewModel.getExpiryYear().observe(this, s -> expiryYear = s);
        loginViewModel.getCardCVV().observe(this, s -> cardCVV = s);
        loginViewModel.getCardPin().observe(this, s -> cardPin = s);

        emailIcon.setOnClickListener(v -> loginPager.setCurrentItem(0, true));
        cardIcon.setOnClickListener(v -> loginPager.setCurrentItem(1, true));
    }

    private void updateIcons(int position)
    {
        if(position == 0)
        {
            emailIcon.setBackgroundResource(R.drawable.login_email_icon_active);
            cardIcon.setBackgroundResource(R.drawable.login_icon_inactive);
            //Toast.makeText(this, "Selected Email Active", Toast.LENGTH_SHORT).show();
        }
        else
        {
            emailIcon.setBackgroundResource(R.drawable.login_icon_inactive);
            cardIcon.setBackgroundResource(R.drawable.login_card_icon_active);
            //Toast.makeText(this, "Selected Card Active", Toast.LENGTH_SHORT).show();
        }
    }
}