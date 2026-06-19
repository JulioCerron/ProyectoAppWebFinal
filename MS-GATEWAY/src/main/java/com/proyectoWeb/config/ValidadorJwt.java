package com.proyectoWeb.config;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class ValidadorJwt {
	@Value("${jjwt.secret}")
	private String secreto;

	public void validarToken(String token) {
		SecretKey clave = Keys.hmacShaKeyFor(secreto.getBytes());
		Jwts.parser().verifyWith(clave).build().parseSignedClaims(token);
	}
}
