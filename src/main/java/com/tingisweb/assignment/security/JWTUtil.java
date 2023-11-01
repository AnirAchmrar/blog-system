package com.tingisweb.assignment.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tingisweb.assignment.errorhandling.exception.ObjectNotFoundException;
import com.tingisweb.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Autowired
    private UserService userService;
    @Value("${jwt_secret}")
    private String secret;
    public static final String USER_NOT_FOUND = "User not found!";

    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {
        if (username!=null) {
            try {
                UserDetails userDetails = userService.loadUserByUsername(username);
                return JWT.create()
                        .withSubject(username)
                        .withClaim("username", userDetails.getUsername())
                        .withIssuedAt(new Date())
                        .withIssuer("blog-system")
                        .withAudience("blog-audience")
                        .withExpiresAt(new Date(System.currentTimeMillis() + (180 * 60 * 1000)))
                        .sign(Algorithm.HMAC256(secret));
            }catch (UsernameNotFoundException e){
                throw new ObjectNotFoundException(USER_NOT_FOUND);
            }
        }else {
            throw new IllegalArgumentException();
        }
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        if (token != null) {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                    .withClaimPresence("username")
                    .withIssuer("blog-system")
                    .withAudience("blog-audience")
                    .build();
            try {
                DecodedJWT decodedJWT = jwtVerifier.verify(token);

                if (decodedJWT.getExpiresAt().before(new Date(System.currentTimeMillis()))) {
                    throw new JWTVerificationException("Token expired!");
                }
                return decodedJWT.getSubject();
            }catch (TokenExpiredException | JWTDecodeException e){
                throw new JWTVerificationException("Invalid token");
            }

        }else {
            throw new JWTVerificationException("Null Token");
        }
    }

}
