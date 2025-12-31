package com.easybid.auth.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.easybid.user.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Service
@Getter
public class JwtService {
  @Value("${spring.application.jwt.secret-key}")
  private String secretKey;

  @Value("${spring.application.jwt.access-token.expiration}")
  private long jwtExpiration;

  @Value("${spring.application.jwt.refresh-token.expiration}")
  private long refreshTokenExpiration;

  public String extractUsername(String token) {
    return this.extractClaim(token, Claims::getSubject);
  }

  public Map<String, Object> getCustomAttributes(String token) {
    Claims claims = extractAllClaims(token);
    Map<String, Object> customAttributes = new HashMap<>(claims);
    customAttributes.remove(Claims.SUBJECT);
    customAttributes.remove(Claims.EXPIRATION);
    customAttributes.remove(Claims.ISSUED_AT);
    return customAttributes;
  }

  public String generateToken(UserDetails userDetails) {
    if (userDetails instanceof UserEntity) {
      UserEntity user = (UserEntity) userDetails;
      Map<String, Object> extraClaims = new HashMap<>();
      extraClaims.put("id", user.getId());
      extraClaims.put("name", user.getName());
      extraClaims.put("role", user.getRole().name());
      return buildToken(extraClaims, userDetails, jwtExpiration);
    }
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  public String generateRefreshToken(
      UserDetails userDetails) {
    if (userDetails instanceof UserEntity) {
      UserEntity user = (UserEntity) userDetails;
      Map<String, Object> extraClaims = new HashMap<>();
      extraClaims.put("id", user.getId());
      extraClaims.put("name", user.getName());
      extraClaims.put("role", user.getRole().name());
      return buildToken(extraClaims, userDetails, refreshTokenExpiration);
    }
    return buildToken(new HashMap<>(), userDetails, refreshTokenExpiration);
  }

  public Claims extractAllClaims(String token) {
    return Jwts
        .parser()
        .verifyWith(this.getSignInKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = this.extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private String buildToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails,
      long expiration) {
    long now = System.currentTimeMillis();
    return Jwts.builder()
        .claims(extraClaims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(now))
        .expiration(new Date(now + expiration))
        .signWith(getSignInKey(), Jwts.SIG.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private SecretKey getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(this.getSecretKey());
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
