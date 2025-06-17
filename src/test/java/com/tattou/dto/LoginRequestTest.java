package com.tattou.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class LoginRequestTest {

    @InjectMocks
    private LoginRequest loginRequest;

    @Test
    public void setEmailTest() {
        String email = "email";
        loginRequest.setEmail(email);
        Assert.isTrue(email.equals(loginRequest.getEmail()), "error");
    }

    @Test
    public void setPasswordTest() {
        String password = "password";
        loginRequest.setPassword(password);
        Assert.isTrue(password.equals(loginRequest.getPassword()), "error");
    }

}
