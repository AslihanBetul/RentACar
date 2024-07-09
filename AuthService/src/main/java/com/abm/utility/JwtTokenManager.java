package com.abm.utility;


import com.abm.entity.Auth;
import com.abm.exception.AuthServiceException;
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
    @Value("${authservice.secret.secret-key}")
    String secretKey;
    @Value("${authservice.secret.issuer}")
    String issuer;
    Long expireTime=1000L*60*60;



    //1. token üretmeli
    public Optional<String> createToken(Auth auth){
        String token="";

        //claim içersindeki değerler herkes tarafından görülebilir.

        try {
            token = JWT.create()
                    .withAudience()
                    .withClaim("id",auth.getId())
                    .withClaim("authRole",auth.getAuthRole().toString())
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis()+expireTime))
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        } catch (IllegalArgumentException e) {
            throw new AuthServiceException(TOKEN_CREATION_FAILED);
        } catch (JWTCreationException e) {
            throw new AuthServiceException(TOKEN_CREATION_FAILED);
        }
    }
    //2. token dpğrulanmalı

//Bakmak lazım
    public boolean verifyToken(DecodedJWT decodedJWT){
        return false;
    }




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
            throw new AuthServiceException(TOKEN_FORMAT_NOT_ACCEPTABLE);
        } catch (JWTVerificationException e) {
            throw new AuthServiceException(TOKEN_VERIFY_FAILED);
        }
    }

    public Optional<String> getAuthRoleFromToken(String token){
        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            decodedJWT = verifier.verify(token);

            if(decodedJWT==null){
                return Optional.empty();
            } else {
                return Optional.of(decodedJWT.getClaim("authRole").asString());
            }

        } catch (IllegalArgumentException e) {
            throw new AuthServiceException(TOKEN_FORMAT_NOT_ACCEPTABLE);
        } catch (JWTVerificationException e) {
            throw new AuthServiceException(TOKEN_VERIFY_FAILED);
        }
    }


}
