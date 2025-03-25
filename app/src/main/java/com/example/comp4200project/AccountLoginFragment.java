package com.example.comp4200project;

import com.example.comp4200project.Utils.LoginType;

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

public class AccountLoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    public interface OnSubmitButtonClickListener
    {
        void onSubmitButtonClicked(LoginType loginType, LoginViewModel loginViewModel);
    }

    private OnSubmitButtonClickListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSubmitButtonClickListener) {
            listener = (OnSubmitButtonClickListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnSubmitButtonClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_account_login, container, false);

        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        TextInputEditText emailInput = view.findViewById(R.id.emailInput);
        TextInputEditText passwordInput = view.findViewById(R.id.passwordInput);
        Button submitButton = view.findViewById(R.id.submitButton);

        emailInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                loginViewModel.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        passwordInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                loginViewModel.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        submitButton.setOnClickListener(v -> {
            String email = Objects.requireNonNull(emailInput.getText()).toString().trim();
            String password = Objects.requireNonNull(passwordInput.getText()).toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(listener != null)
                {
                    listener.onSubmitButtonClicked(LoginType.ACCOUNT, loginViewModel);
                }
            }
        });

        return view;
    }
}
