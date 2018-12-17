package com.abcenterprise.ppmtool.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import static com.abcenterprise.ppmtool.security.SecurityConstants.EXPIRATION_TIME;
import static com.abcenterprise.ppmtool.security.SecurityConstants.SECRET;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.abcenterprise.ppmtool.domain.user.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtTokenProvider {

	public String generateToken(Authentication authentication) {

		User user = (User) authentication.getPrincipal();

		String username = user.getUsername();
		String fullName = user.getFullName();
		String userId = Long.toString(user.getId());

		Map<String, Object> claims = new HashMap<>();

		claims.put("username", username);
		claims.put("fullName", fullName);
		claims.put("id", userId);

		Date now = new Date(System.currentTimeMillis());
		Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

		return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}
	/*
	 * Accept token string and and return boolean 
	 * use Jwts instance to validate by passing SCRET key and token 
	 */
 public boolean validate (String token) {
	      try {
	    	   Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
	    	   return true; // important to set the authentication flag otherwise request will not reach the route
	    	   
	      }catch(ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException ex) {
	      }
	 return false;
 }
	/*
	 * accept jwt token and return userId as Long by parsing jwts instance passing
	 * SECRET key and token string
	 */
	public Long getUserIdFromJwt(String token) {
		Claims claim = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String id = (String) claim.get("id");
		return Long.parseLong(id);
	}
}
