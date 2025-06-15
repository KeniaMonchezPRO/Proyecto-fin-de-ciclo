package com.example.vineta_virtual.auth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.example.vineta_virtual.LoginService;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @Mock
    LoginService.AuthApi mockAuthApi;


    private LoginService loginService;

    @Before
    public void setup() {
        loginService = new LoginService(mockAuthApi);
    }


    @Test
    public void login_success_returnsTrue() {
        when(mockAuthApi.authenticate("testUser", "password123")).thenReturn(true);

        boolean result = loginService.login("testUser", "password123");

        assertTrue(result);
    }

    @Test
    public void login_wrongPassword_returnsFalse() {
        when(mockAuthApi.authenticate("testUser", "wrongPass")).thenReturn(false);

        boolean result = loginService.login("testUser", "wrongPass");

        assertFalse(result);
    }

    @Test
    public void login_userNotFound_returnsFalse() {
        when(mockAuthApi.authenticate("nonExistentUser", "anyPass")).thenReturn(false);

        boolean result = loginService.login("nonExistentUser", "anyPass");

        assertFalse(result);
    }

    @Test
    public void login_emptyUsername_returnsFalse() {
        boolean result = loginService.login("", "password");
        assertFalse(result);
    }

    @Test
    public void login_nullUsername_returnsFalse() {
        boolean result = loginService.login(null, "password");
        assertFalse(result);
    }

    @Test
    public void login_emptyPassword_returnsFalse() {
        boolean result = loginService.login("username", "");
        assertFalse(result);
    }

    @Test
    public void login_nullPassword_returnsFalse() {
        boolean result = loginService.login("username", null);
        assertFalse(result);
    }
}
