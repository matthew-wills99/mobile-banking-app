package com.example.comp4200project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

public class CardLoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_card_login, container, false);

        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        TextInputEditText cardNumberInput = view.findViewById(R.id.cardNumberInput);
        TextInputEditText expiryMonthInput = view.findViewById(R.id.monthInput);
        TextInputEditText expiryYearInput = view.findViewById(R.id.yearInput);
        TextInputEditText cardCVVInput = view.findViewById(R.id.cvvInput);
        TextInputEditText pinInput = view.findViewById(R.id.pinInput);

        cardNumberInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                loginViewModel.setAccountNumber(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        expiryMonthInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                loginViewModel.setExpiryMonth(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        expiryYearInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                loginViewModel.setExpiryYear(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        cardCVVInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                loginViewModel.setCardCVV(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        pinInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                loginViewModel.setCardPin(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }
}
