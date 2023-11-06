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
import com.tingisweb.assignment.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * A service class for generating and validating JWT (JSON Web Token) tokens.
 */
@Component
public class JWTUtil {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Value("${jwt_secret}")
    private String secret;
    public static final String USER_NOT_FOUND = "User not found!";

    /**
     * Generates a JWT token for a given username.
     *
     * @param username The username for which the token is generated.
     * @return The generated JWT token as a String.
     * @throws IllegalArgumentException   If the input username is null.
     * @throws JWTCreationException       If there is an issue with JWT token creation.
     * @throws ObjectNotFoundException     If the user with the provided username is not found.
     */
    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException,
            ObjectNotFoundException {
        if (username!=null) {
            try {
                UserDetails userDetails = userServiceImpl.loadUserByUsername(username);
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

    /**
     * Validates a JWT token and retrieves the subject (username) from it.
     *
     * @param token The JWT token to validate.
     * @return The subject (username) from the validated token.
     * @throws JWTVerificationException If the token is not valid or has expired.
     */
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
