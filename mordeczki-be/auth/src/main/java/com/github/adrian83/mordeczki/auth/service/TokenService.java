package com.github.adrian83.mordeczki.auth.service;

import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.github.adrian83.mordeczki.auth.model.command.TokenRequest;

@Service
public class TokenService {

  private static final Long EXPIRE_IN_MILLIS = 1000L * 60 * 60 * 2; // 2h
  private static final String SECRET = "fa23hgrb23rv2394g0x81hyr275tcgr23gxc2435g43og527x";
  private static final String ISSUER = "mordeczki";

  private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
  private static final JWTVerifier VERIFIER = JWT.require(ALGORITHM).withIssuer(ISSUER).build();

  public String createToken(TokenRequest data) {
    try {
      return JWT.create()
          .withSubject(data.getEmail())
          .withArrayClaim("roles", data.getRoles().toArray(new String[0]))
          .withIssuer(ISSUER)
          .withExpiresAt(expirationDate())
          .sign(ALGORITHM);

    } catch (JWTCreationException ex) {
      // TODO refactor
      throw new RuntimeException(ex);
    }
  }

  public TokenRequest decodeToken(String token) {
    try {
      DecodedJWT jwt = VERIFIER.verify(token);
      var email = jwt.getSubject();
      var roles = jwt.getClaim("roles").asArray(String.class);
      
      return TokenRequest.builder().email(email).roles(Arrays.asList(roles)).build();
    } catch (JWTVerificationException ex) {
      // TODO refactor
      throw new RuntimeException(ex);
    }
  }

  private Date expirationDate() {
    return new Date(System.currentTimeMillis() + EXPIRE_IN_MILLIS);
  }
}
