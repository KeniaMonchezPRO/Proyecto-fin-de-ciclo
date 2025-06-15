package com.example.vineta_virtual;

public class LoginValidator {
    public boolean validate(String email, String password) {
        return email != null && !email.isEmpty() &&
                password != null && password.length() >= 6;
    }
}

