package com.example.vineta_virtual;

public class LoginService {
    public interface AuthApi {
        boolean authenticate(String username, String password);
    }

    private final AuthApi authApi;

    public LoginService(AuthApi authApi) {
        this.authApi = authApi;
    }

    public boolean login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        return authApi.authenticate(username, password);
    }
}


