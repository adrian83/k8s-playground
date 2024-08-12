package com.github.adrian83.mordeczki.auth.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.github.adrian83.mordeczki.auth.exception.TokenCreationException;
import com.github.adrian83.mordeczki.auth.exception.TokenVerificationException;
import com.github.adrian83.mordeczki.auth.model.command.CreateTokenCommand;
import com.github.adrian83.mordeczki.auth.model.payload.TokenDataPayload;
import com.github.adrian83.mordeczki.auth.model.payload.TokenType;



public class AuthTokenService {

    private static final Long TOKEN_EXPIRATION_HOURS = 2L;
    private static final ZoneId UTC_ZONE = ZoneId.of("UTC");

    private static final String SECRET = "fa23hgrb23rv2394g0x81hyr275tcgr23gxc2435g43og527x";
    private static final String ISSUER = "mordeczki";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static final JWTVerifier VERIFIER = JWT.require(ALGORITHM).withIssuer(ISSUER).build();

    private static final String CLAIM_ROLES = "roles";

    


    public String createToken(CreateTokenCommand data) {
        try {
            var tokenBody = JWT.create()
                    .withSubject(data.email())
                    .withArrayClaim(CLAIM_ROLES, data.roles().toArray(new String[0]))
                    .withIssuer(ISSUER)
                    .withExpiresAt(toExpirationDate(data.expirationDate()))
                    .sign(ALGORITHM);
            return createBearerToken(tokenBody);
        } catch (JWTCreationException ex) {
            throw new TokenCreationException("Cannot create token with data. Data: " + data, ex);
        }
    }

    public TokenDataPayload decodeToken(String token) {
        try {
            var tokenBody = extractTokenBody(token);
            DecodedJWT jwt = VERIFIER.verify(tokenBody);
            var email = jwt.getSubject();
            var date = fromExpirationDate(jwt.getExpiresAt());
            var roles = jwt.getClaim(CLAIM_ROLES).asArray(String.class);
            return new TokenDataPayload(email, Arrays.asList(roles), date);
        } catch (JWTVerificationException ex) {
            throw new TokenVerificationException("Cannot decode token. Token: " + token, ex);
        }
    }

    private String extractTokenBody(String token) {
        if (!token.startsWith(TokenType.BEARER.label())) {
            throw new TokenVerificationException("Invalid token format: invalid prefix. Token: " + token);
        }

        return token.substring(TokenType.BEARER.label().length()).strip();
    }

    private String createBearerToken(String tokenBody) {
        return "%s %s".formatted(TokenType.BEARER.label(), tokenBody);
    }

    private Date toExpirationDate(ZonedDateTime validTo) {
        var expirationDateTime = validTo.plusHours(TOKEN_EXPIRATION_HOURS);
        return Date.from(expirationDateTime.toInstant());
    }

    private ZonedDateTime fromExpirationDate(Date validTo) {
        return ZonedDateTime.ofInstant(validTo.toInstant(), UTC_ZONE);
    }
}
