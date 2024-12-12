package com.example.app.authentication.controller;

import com.example.app.authentication.model.Authorization;
import com.example.app.authentication.model.Credentials;
import com.example.app.authentication.repository.PermissionRepository;
import com.example.app.authentication.service.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final IAuthenticationService authenticationService;
    private final PermissionRepository permissionRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<Authorization> authenticate(@RequestBody @Valid Credentials credentials) {
        Authorization authorization = this.authenticationService.getToken(credentials);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, this.authenticationService.createRefreshToken(credentials.getLogin()).toString());
        return ResponseEntity.status(200).headers(responseHeaders).body(authorization);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Authorization> refreshToken(@CookieValue(name = "ldap-refresh-cookie", required = false) String refreshToken) {
        Authorization authorization = this.authenticationService.refreshToken(refreshToken);
        return ResponseEntity.status(200).body(authorization);
    }
}
