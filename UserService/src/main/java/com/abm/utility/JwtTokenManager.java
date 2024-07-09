package com.abm.utility;


import com.abm.exception.UserServiceException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static com.abm.exception.ErrorType.*;




@Component
public class JwtTokenManager {
    @Value("${userservice.secret.secret-key}")
    String secretKey;
    @Value("${userservice.secret.issuer}")
    String issuer;
    Long expireTime=1000L*60*60;



    //1. token üretmeli




    //3. tokendan bilgi çıkarımı yapılmalı
    public Optional<Long> getAuthIdFromToken(String token){
        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            decodedJWT = verifier.verify(token);

            if(decodedJWT==null){
                return Optional.empty();
            } else {
                return Optional.of(decodedJWT.getClaim("id").asLong());
            }

        } catch (IllegalArgumentException e) {
            throw new UserServiceException(TOKEN_FORMAT_NOT_ACCEPTABLE);
        } catch (JWTVerificationException e) {
            throw new UserServiceException(TOKEN_VERIFY_FAILED);
        }
    }




}
