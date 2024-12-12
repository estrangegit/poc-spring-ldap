package com.example.app.authentication.service;

import com.example.app.authentication.model.Authorization;
import com.example.app.authentication.model.Credentials;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.User;

public interface IAuthenticationService {
    Authorization getToken(Credentials credentials);
    User readAccessToken(String accessToken);
    Authorization refreshToken(String refreshToken);
    ResponseCookie createRefreshToken(String login);
}
