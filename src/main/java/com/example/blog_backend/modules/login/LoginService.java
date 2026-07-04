package com.example.blog_backend.modules.login;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.blog_backend.modules.login.dto.LoginResponseDTO;
import com.example.blog_backend.modules.user.User;
import com.example.blog_backend.modules.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class LoginService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Value("${api.security.token.secret}")
    private String secret;

    public LoginService(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(String email, String password){

        User user = userService.findByEmail(email);

        if(!passwordEncoder.matches(password, user.getPassword())){
            return null;
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withIssuer("blog-backend")
                .withSubject(user.getId().toString())
                .withClaim("role", user.getRole().name())
                .withExpiresAt(this.generateTokenExpiration())
                .sign(algorithm);

        return new LoginResponseDTO(token, user.getId().toString());
    }

    public UsernamePasswordAuthenticationToken validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("blog-backend")
                    .build()
                    .verify(token);

            String userId = decodedJWT.getSubject();
            String role = decodedJWT.getClaim("role").asString();

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

            return new UsernamePasswordAuthenticationToken(userId, null, Collections.singletonList(authority));

        }catch (JWTVerificationException exception){
            return null;
        }

    }


    private Instant generateTokenExpiration() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
