package com.example.beprojec2.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class JwtUtilHelper {
    @Value("${jwt.privateKey}")
    private String privateKey;
    // từ key tạo ra token cho user
    public String generateToken(String username) {
        SecretKey key= Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
        String jws= Jwts.builder()
                .setSubject(username)
                .signWith(key)
                .compact();
        return jws;
    }
    // kiểm tra token có phải do key của server tạo ra không
    public boolean verifyToken(String token) {
        boolean isVerifiy = false;
        try{
            SecretKey key= Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            isVerifiy = true;
        } catch (Exception e) {
            System.out.println("Error verify token "+ e.getMessage());
            isVerifiy = false;
        }
        return isVerifiy;
    }
    public String getUsername(String token) {
        SecretKey key= Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
        @SuppressWarnings("deprecation")
        String username = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody().getSubject();
        return username;
    }
}
