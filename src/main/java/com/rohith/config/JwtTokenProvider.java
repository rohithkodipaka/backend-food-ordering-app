package com.rohith.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenProvider {

	private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public String generateToken(Authentication auth) {
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		String roles = populateAuthorities(authorities);
		String jwt=Jwts.builder().setIssuedAt(new Date())
								 .setExpiration(new Date(new Date().getTime()+1000*60*60*24))
								 .claim("email",auth.getName())
								 .claim("authorities",roles)
								 .signWith(key)
								 .compact();
		return jwt;
	}
	
	public String getEmailFromJwtToken(String jwt) {
		jwt = jwt.substring(7);
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		return String.valueOf(claims.get("email"));
	}

	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> auth = new HashSet<>();
		for(GrantedAuthority authority:authorities) {
			auth.add(authority.getAuthority());
		}
		return String.join(",",auth);
	}
}
