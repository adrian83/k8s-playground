package com.github.adrian83.mordeczki.auth.service;

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

public class TokenService {

	private static final Long EXPIRE_IN_MILLIS = 1000L * 60 * 60 * 2; // 2h

	private static final String SECRET = "fa23hgrb23rv2394g0x81hyr275tcgr23gxc2435g43og527x";
	private static final String ISSUER = "mordeczki";

	private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
	private static final JWTVerifier VERIFIER = JWT.require(ALGORITHM).withIssuer(ISSUER).build();

	private static final String CLAIM_ROLES = "roles";

	public String createToken(CreateTokenCommand data) {
		try {
			return JWT.create()
					.withSubject(data.email())
					.withArrayClaim(CLAIM_ROLES, data.roles().toArray(new String[0]))
					.withIssuer(ISSUER)
					.withExpiresAt(expirationDate())
					.sign(ALGORITHM);
		} catch (JWTCreationException ex) {
			var msg = "cannot create token with data: %s".formatted(data);
			throw new TokenCreationException(msg, ex);
		}
	}

	public CreateTokenCommand decodeToken(String token) {
		try {
			DecodedJWT jwt = VERIFIER.verify(token);
			var email = jwt.getSubject();
			var roles = jwt.getClaim(CLAIM_ROLES).asArray(String.class);
			return new CreateTokenCommand(email, Arrays.asList(roles));
		} catch (JWTVerificationException ex) {
			var msg = "cannot decode token '%s'".formatted(token);
			throw new TokenVerificationException(msg, ex);
		}
	}

	private Date expirationDate() {
		return new Date(System.currentTimeMillis() + EXPIRE_IN_MILLIS);
	}
}
