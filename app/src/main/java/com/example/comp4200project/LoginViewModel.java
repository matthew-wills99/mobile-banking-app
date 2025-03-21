package com.example.comp4200project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    // Email Login
    private final MutableLiveData<String> emailLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> passwordLiveData = new MutableLiveData<>();

    // Card Login
    private final MutableLiveData<String> cardNumberLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> expiryMonthLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> expiryYearLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> cardCVVLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> cardPinLiveData = new MutableLiveData<>();

    public void setEmail(String email)
    {
        emailLiveData.setValue(email);
    }

    public void setPassword(String password)
    {
        passwordLiveData.setValue(password);
    }

    public void setCardNumber(String cardNumber)
    {
        cardNumberLiveData.setValue(cardNumber);
    }

    public void setExpiryMonth(String expiryMonth)
    {
        expiryMonthLiveData.setValue(expiryMonth);
    }

    public void setExpiryYear(String expiryYear)
    {
        expiryYearLiveData.setValue(expiryYear);
    }

    public void setCardCVV(String cardCVV)
    {
        cardCVVLiveData.setValue(cardCVV);
    }

    public void setCardPin(String cardPin)
    {
        cardPinLiveData.setValue(cardPin);
    }

    public LiveData<String> getEmail()
    {
        return emailLiveData;
    }

    public LiveData<String> getPassword()
    {
        return passwordLiveData;
    }

    public LiveData<String> getCardNumber()
    {
        return cardNumberLiveData;
    }

    public LiveData<String> getExpiryMonth()
    {
        return expiryMonthLiveData;
    }

    public LiveData<String> getExpiryYear()
    {
        return expiryYearLiveData;
    }

    public LiveData<String> getCardCVV()
    {
        return cardCVVLiveData;
    }

    public LiveData<String> getCardPin()
    {
        return cardPinLiveData;
    }
}
