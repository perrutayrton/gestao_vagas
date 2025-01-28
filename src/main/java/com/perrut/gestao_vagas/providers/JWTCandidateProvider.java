package com.perrut.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTCandidateProvider {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    public DecodedJWT validateToken(String token) {

        try {

            return JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token.replace("Bearer ", ""));

        } catch (JWTVerificationException e) {
           e.printStackTrace();
           return null;
        }
    }
}
