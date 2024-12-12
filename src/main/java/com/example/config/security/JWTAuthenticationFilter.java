package com.example.config.security;

import com.example.app.authentication.service.implementation.AuthenticationService;
import com.example.exception.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    
    private final AuthenticationService authenticationService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)).map(value -> StringUtils.removeStart(value, BEARER));
        try{
            if (token.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            User user = this.authenticationService.readAccessToken(token.get());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(AuthorizationException | ExpiredAuthorizationException | SignatureAuthorizationException exception) {
            if(exception instanceof ExpiredAuthorizationException) {
                sendCustomHttpError(request, response, exception, ErrorCode.EXPIRED_AUTHORIZATION_EXCEPTION);
            } else if (exception instanceof SignatureAuthorizationException) {
                sendCustomHttpError(request, response, exception, ErrorCode.SIGNATURE_AUTHORIZATION_EXCEPTION);
            } else {
                sendCustomHttpError(request, response, exception, ErrorCode.AUTHORIZATION_EXCEPTION);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void sendCustomHttpError(HttpServletRequest request, HttpServletResponse response, StatusAndMessageException exception, ErrorCode errorCode) throws IOException {
        CustomHttpError customHttpError = CustomHttpError.builder()
                .timestamp(new Date()).status(exception.getStatus().value())
                .error(exception.getStatus().getReasonPhrase())
                .path(request.getRequestURI())
                .customError(new ErrorDetails(errorCode, exception.getMessage())).build();
        response.setStatus(exception.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        this.objectMapper.writeValue(response.getWriter(), customHttpError);
    }
}
