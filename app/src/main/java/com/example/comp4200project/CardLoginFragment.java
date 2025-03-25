package com.example.comp4200project;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CardLoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    private AccountLoginFragment.OnSubmitButtonClickListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AccountLoginFragment.OnSubmitButtonClickListener) {
            listener = (AccountLoginFragment.OnSubmitButtonClickListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnSubmitButtonClickListener");
        }
    }

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
        Button submitButton = view.findViewById(R.id.submitButton);

        cardNumberInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                loginViewModel.setCardNumber(s.toString());
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

        submitButton.setOnClickListener(v -> {
            String cardNumber = Objects.requireNonNull(cardNumberInput.getText()).toString().trim();
            String expiryMonth = Objects.requireNonNull(expiryMonthInput.getText()).toString().trim();
            String expiryYear = Objects.requireNonNull(expiryYearInput.getText()).toString().trim();
            String cardCVV = Objects.requireNonNull(cardCVVInput.getText()).toString().trim();
            String cardPin = Objects.requireNonNull(pinInput.getText()).toString().trim();


            if (cardNumber.isEmpty() || expiryMonth.isEmpty() || expiryYear.isEmpty() || cardCVV.isEmpty() || cardPin.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(listener != null)
                {
                    listener.onSubmitButtonClicked(Utils.LoginType.CARD, loginViewModel);
                }
            }
        });

        return view;
    }
}
