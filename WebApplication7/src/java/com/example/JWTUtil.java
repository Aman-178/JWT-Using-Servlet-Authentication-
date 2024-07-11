package com.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import java.util.Date;

public class JWTUtil {

    public static String generateToken(String username, String secretKey) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, Base64.decodeBase64(secretKey))
                .compact();
    }

    public static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(Base64.decodeBase64(secretKey))
                .parseClaimsJws(token)
                .getBody();
    }

    public static String extractUsername(String token, String secretKey) {
        return extractClaims(token, secretKey).getSubject();
    }

    public static boolean validateToken(String token, String secretKey) {
        try {
            Jwts.parser().setSigningKey(Base64.decodeBase64(secretKey)).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
