package com.abm.utility;


import com.abm.entity.Auth;
import com.abm.exception.AuthServiceException;
import com.abm.exception.ErrorType;
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

    //1. Token uretmeli
    public Optional<String> createToken(String id) {
        String token = "";
        try {
            token = JWT.create()
                    .withClaim("id", id)
                    .withClaim("whichpage", "AdminService")
                    .withClaim("class", "Java JWT")
                    .withClaim("group", "carrental")
                    .withIssuer("car rental")
                    .withIssuedAt(new Date()) // Tokenin yaratildigi an
                    .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        } catch (IllegalArgumentException e) {
            throw new AuthServiceException(ErrorType.TOKEN_CREATION_FAILED);
        } catch (JWTCreationException e) {
            throw new AuthServiceException(ErrorType.TOKEN_CREATION_FAILED);
        }

    }

/*public Optional<String> createDoubleToken(Long id, Role role){
        String doubleToken = "";
        try {
            doubleToken = JWT.create()
                    .withClaim("id", id)
                    .withClaim("role", role.toString())
                    .withClaim("whichpage", "AuthMicroService")
                    .withClaim("ders", "Java JWT")
                    .withClaim("group", "Java14")
                    .withIssuer("Java14")
                    .withIssuedAt(new Date()) // Tokenin yaratildigi an
                    .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(doubleToken);
        } catch (IllegalArgumentException e) {
            throw new AuthServiceException(ErrorType.TOKEN_CREATION_FAILED);
        } catch (JWTCreationException e) {
            throw new AuthServiceException(ErrorType.TOKEN_CREATION_FAILED);
        }

    }*/


    //2. Token Dogrulanmali
//    public Optional<Long> verifyToken(String token) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC512(secretKey);
//            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
//            DecodedJWT decodedJWT = verifier.verify(token);
//
//            if (decodedJWT==null) {
//                return Optional.empty();
//            }
//            Long id = decodedJWT.getClaim("id").asLong();
//
//            return Optional.of(id);
//
//        } catch (IllegalArgumentException e) {
//            throw new AuthServiceException((ErrorType.TOKEN_ARGUMENT_NOTVALID));
//        } catch (JWTVerificationException e) {
//            throw new AuthServiceException(ErrorType.TOKEN_VERIFY_FAILED);
//        }
//
//    }

    //3 Tokendan bilgi cakirimi yapmali
    public Optional<String> getIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            if (decodedJWT == null) {
                return Optional.empty();
            }
            String id = decodedJWT.getClaim("id").asString();

            return Optional.of(id);

        } catch (IllegalArgumentException e) {
            throw new AuthServiceException((ErrorType.TOKEN_ARGUMENT_NOTVALID));
        } catch (JWTVerificationException e) {
            throw new AuthServiceException(ErrorType.TOKEN_VERIFY_FAILED);
        }
    }
    public Optional<String> getNameFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            if (decodedJWT == null) {
                return Optional.empty();
            }
            String name = decodedJWT.getClaim("name").asString();

            return Optional.of(name);

        } catch (IllegalArgumentException e) {
            throw new AuthServiceException((ErrorType.TOKEN_ARGUMENT_NOTVALID));
        } catch (JWTVerificationException e) {
            throw new AuthServiceException(ErrorType.TOKEN_VERIFY_FAILED);
        }
    }
    public Optional<String> getPasswordFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            if (decodedJWT == null) {
                return Optional.empty();
            }
            String password = decodedJWT.getClaim("password").asString();

            return Optional.of(password);

        } catch (IllegalArgumentException e) {
            throw new AuthServiceException(ErrorType.TOKEN_ARGUMENT_NOTVALID);
        } catch (JWTVerificationException e) {
            throw new AuthServiceException(ErrorType.TOKEN_VERIFY_FAILED);
        }
    }



    public Optional<String> getRoleFromToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            if (decodedJWT==null) {
                return Optional.empty();
            }
            String role = decodedJWT.getClaim("role").asString();

            return Optional.of(role);

        } catch (IllegalArgumentException e) {
            throw new AuthServiceException((ErrorType.TOKEN_ARGUMENT_NOTVALID));
        } catch (JWTVerificationException e) {
            throw new AuthServiceException(ErrorType.TOKEN_VERIFY_FAILED);
        }
    }

}


