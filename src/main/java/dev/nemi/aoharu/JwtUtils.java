package dev.nemi.aoharu;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
  private final String jwtSecret = "sioudfnbvisjadbsvoicuafdybviasuehfiasodbuzifvubozisdufcoisdnjxoisdjhnofijlbkzbudofivbjoaihfjvbokldsafbjvlasidfboviausdobfviaosdfv";
  private final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  private final int jwtExpirationMs = 1000 * 60 * 60 * 24; // 24 hours

  public String generateJwt(String username) {
    return Jwts.builder()
      .subject(username)
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//      .signWith(SignatureAlgorithm.HS256, jwtSecret)
      .signWith(secretKey)
      .compact();
  }

  public String generateJwt(Authentication authentication) {
    return Jwts.builder()
      .subject(authentication.getName())
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
      .claim("authorities", authentication.getAuthorities())
      .signWith(secretKey)
      .compact();
  }

  public String getUsernameFromJwt(String token) {
//    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
  }

  public boolean validateJwt(String token) {
    try {
//      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
