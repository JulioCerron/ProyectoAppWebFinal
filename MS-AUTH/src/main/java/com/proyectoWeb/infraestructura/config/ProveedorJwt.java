package com.proyectoWeb.infraestructura.config;

import com.proyectoWeb.dominio.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProveedorJwt {

	@Value("${jjwt.secret}")
	private String secreto;

	@Value("${jjwt.expiration}")
	private long expiracion;

	public String generarToken(Usuario usuario) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", usuario.getRoles());

		SecretKey key = Keys.hmacShaKeyFor(secreto.getBytes());

		return Jwts.builder().claims(claims).subject(usuario.getNombreUsuario())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiracion * 1000)).signWith(key).compact();
	}
}