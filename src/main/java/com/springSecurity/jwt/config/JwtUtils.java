package com.springSecurity.jwt.config;

import com.springSecurity.jwt.JwtApplication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    private String SECRET_KEY = "secret";

    public String extractUsername(String token) {
        System.out.println("inside jwt util class  extract user name--> step "+ ++JwtApplication.STEP);

        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        System.out.println("inside jwt util class  is expired token--> step "+ ++JwtApplication.STEP);

        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        System.out.println("inside jwt util class  generate token--> step "+ ++JwtApplication.STEP);

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities",userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        System.out.println("inside jwt util class  create token--> step "+ ++JwtApplication.STEP);

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        System.out.println("inside jwt util class  validate token--> step "+ ++JwtApplication.STEP);

        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}