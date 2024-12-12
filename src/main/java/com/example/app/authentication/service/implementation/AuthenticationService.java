package com.example.app.authentication.service.implementation;

import com.example.app.authentication.model.Agent;
import com.example.app.authentication.model.Authorization;
import com.example.app.authentication.model.Credentials;
import com.example.app.authentication.model.Permission;
import com.example.app.authentication.repository.AgentRepository;
import com.example.app.authentication.repository.PermissionRepository;
import com.example.app.authentication.service.IAuthenticationService;
import com.example.exception.model.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.ldap.LdapName;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final AgentRepository agentRepository;
    private final PermissionRepository permissionRepository;
    
    @Value("${application.jwt.secret.key}")
    private String secretKey;

    @Value("${application.jwt.access.token.validity.min}")
    private int accessTokenValidity;

    @Value("${application.jwt.refresh.token.validity.min}")
    private int resfreshTokenValidity;

    @Value("${ldap.base}")
    private String baseDn;

    public Authorization getToken(Credentials credentials) {
        try {
            Optional<Agent> optAgent = this.agentRepository.findByLoginAndPassword(credentials.getLogin(), credentials.getPassword());
            if(optAgent.isEmpty()) {
                throw new AuthenticationException("Wrong credentials (login: " + credentials.getLogin() + ")");
            }
            Agent agent = optAgent.get();
            String token = createAccessToken(agent);
            return new Authorization(agent.getLogin(), token);            
        } catch (InvalidNameException invalidNameException) {
            throw new AuthenticationException(invalidNameException.getMessage());
        }
    }

    public User readAccessToken(String accessToken) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            JwtParser jwtParser = Jwts.parser().verifyWith(key).build();
            Claims claims = jwtParser.parseSignedClaims(accessToken).getPayload();
            String roles = (String)claims.get("roles");
            Set<GrantedAuthority> authorities = Arrays.stream(roles.split(", ")).map(role -> "ROLE_" + role).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
            return new User(claims.get("login").toString(), "", true, true, true, true, authorities);
        } catch(ExpiredJwtException expiredJwtException) {
            throw new ExpiredAuthorizationException(expiredJwtException.getMessage());
        } catch(SignatureException signatureException) {
            throw new SignatureAuthorizationException(signatureException.getMessage());
        } catch (Exception exception) {
            throw new AuthorizationException(exception.getMessage());
        }
    }

    public Authorization refreshToken(String refreshToken) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            JwtParser jwtParser = Jwts.parser().verifyWith(key).build();
            Claims claims = jwtParser.parseSignedClaims(refreshToken).getPayload();
            String login = claims.getSubject();
            Optional<Agent> optAgent = this.agentRepository.findByLogin(login);
            if(optAgent.isEmpty()) {
                throw new AuthenticationException("Unknown agent for login " + login);
            }
            Agent agent = optAgent.get();
            String newAccessToken = this.createAccessToken(agent);
            return new Authorization(agent.getLogin(), newAccessToken);
        } catch (Exception exception) {
            throw new RefreshTokenException(exception.getMessage());
        }
    }

    public ResponseCookie createRefreshToken(String login) {
        Date tokenIssuedAt = new Date();
        Date tokenExpiration = new Date(tokenIssuedAt.getTime() + TimeUnit.MINUTES.toMillis(this.resfreshTokenValidity));
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        String refreshToken = Jwts.builder().subject(login).issuedAt(tokenIssuedAt).expiration(tokenExpiration).signWith(key, Jwts.SIG.HS256).compact();
        return ResponseCookie.from("ldap-refresh-cookie", refreshToken).httpOnly(true).path("/api/public/refresh").build();
    }

    private String createAccessToken(Agent agent) throws InvalidNameException {
        Name baseName = new LdapName(this.baseDn);
        Name fullAgentDn = baseName.addAll(agent.getDn());
        List<Permission> permissions = this.permissionRepository.findByMembers(fullAgentDn);

        Claims claims = Jwts.claims().add("login", agent.getLogin())
                .add("fullName", agent.getFullName())
                .add("roles", permissions.stream().map(Permission::getName).collect(Collectors.joining(", ")))
                .build();

        Date tokenIssuedAt = new Date();
        Date tokenExpiration = new Date(tokenIssuedAt.getTime() + TimeUnit.MINUTES.toMillis(this.accessTokenValidity));
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder().subject(agent.getLogin()).claims(claims).issuedAt(tokenIssuedAt).expiration(tokenExpiration).signWith(key, Jwts.SIG.HS256).compact();
    }
}
