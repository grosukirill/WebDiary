package com.ru.questiondiary.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ru.questiondiary.web.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.token.secret}")
    private String TOKEN_SECRET;
    @Getter
    @Value("${jwt.token.validity}")
    private Integer TOKEN_VALIDITY;


    public String createToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Date now = new Date();
            return "Bearer " + JWT.create()
                    .withClaim("username", user.getUsername())
                    .withClaim("userId", user.getId().toString())
                    .withExpiresAt(new Date(now.getTime() + TOKEN_VALIDITY))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public Map<String, String> getUserDataFromToken(String token) {
        Map<String, String> result = new HashMap<>();
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            result.put("email", jwt.getClaim("username").asString());
            result.put("id", jwt.getClaim("userId").asString());
            return result;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }

}
