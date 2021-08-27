package com.ru.questiondiary.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ru.questiondiary.web.dto.ErrorCode;
import com.ru.questiondiary.web.dto.ErrorDto;
import com.ru.questiondiary.web.dto.ErrorResponse;
import com.ru.questiondiary.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.token.secret}")
    private String TOKEN_SECRET;

//    @Value("${jwt.token.validity}")
//    private Integer TOKEN_VALIDITY;


    public String createToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Date now = new Date();
            return JWT.create()
                    .withClaim("username", user.getUsername())
                    .withClaim("userId", user.getId().toString())
//                    .withClaim("createdAt", now)
//                    .withExpiresAt(new Date(now.getTime() + TOKEN_VALIDITY))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ErrorResponse createTokenException() {
        ErrorDto errorDto = new ErrorDto("Invalid Token.", ErrorCode.INVALID_TOKEN.number, ErrorCode.INVALID_TOKEN, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return new ErrorResponse(errorDto);
    }


    public Map<String, String> getUserDataFromToken(String rawToken) {
        String token = rawToken;
        if (rawToken.startsWith("Bearer ")) {
            token = rawToken.substring(7);
        }
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
