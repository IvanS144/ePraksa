package org.unibl.etf.epraksa.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.unibl.etf.epraksa.exceptions.UnauthorizedException;
import org.unibl.etf.epraksa.model.dataTransferObjects.UserDTO;
import org.unibl.etf.epraksa.model.replies.LoginReply;
import org.unibl.etf.epraksa.model.requests.LoginRequest;
import org.unibl.etf.epraksa.services.AuthService;
import org.unibl.etf.epraksa.util.LoggingUtil;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final EpraksaUserDetailsServiceImpl ePraksaUserDetailsService;
    @Value("${authorization.token.expiration-time}")
    private String tokenExpirationTime;
    @Value("${authorization.token.secret}")
    private String tokenSecret;


    public AuthServiceImpl(AuthenticationManager authenticationManager, EpraksaUserDetailsServiceImpl ePraksaUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.ePraksaUserDetailsService = ePraksaUserDetailsService;
    }

    @Override
    public ResponseEntity<LoginReply> login(LoginRequest request) {
        ResponseEntity<LoginReply> response = null;
        try {
            UserDTO user = ePraksaUserDetailsService.loadUserByUsername(request.getEmail());
            if(!user.isEnabled())
                throw new Exception();
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getEmail(), request.getPassword()
                            )
                    );
            user = (UserDTO) authenticate.getPrincipal();
            response = ResponseEntity.ok().body(new LoginReply(generateJwt(user)));
        } catch (Exception ex) {
            LoggingUtil.logException(ex, getClass());
            throw new UnauthorizedException("Neispravni kredencijali");
        }
        return response;
    }

    private String generateJwt(UserDTO user) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }
}
