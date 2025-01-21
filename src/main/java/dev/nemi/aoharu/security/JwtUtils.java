package dev.nemi.aoharu.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
  private static final String AUTHORITIES_KEY = "auth";

  private final String jwtSecret;
  private final SecretKey secretKey;
  private final int jwtExpirationMs; // 24 hours


  public JwtUtils(
    @Value("${dev.nemi.aoharu.jwt.secret}") String jwtSecret,
    @Value("${dev.nemi.aoharu.jwt.expire-day}") int day
  ) {
    this.jwtSecret = jwtSecret;
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    this.jwtExpirationMs = day * 24 * 60 * 60 * 1000;
  }

  public String createJwt(Authentication authentication) {
    String authority = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
    return Jwts.builder()
      .subject(authentication.getName())
      .header().add("typ","JWT").and()
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
      .claim(AUTHORITIES_KEY, authority)
      .signWith(secretKey)
      .compact();
  }

  public Authentication getAuthentication(String jwt) {
    Claims claim = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
    List<? extends GrantedAuthority> authorities = Arrays.stream(claim.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new).toList();
    org.springframework.security.core.userdetails.User principal
       = new org.springframework.security.core.userdetails.User(claim.getSubject(), "secret", authorities);
    return new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
  }


  public boolean validateJwt(String jwt) {
    try {
      Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
