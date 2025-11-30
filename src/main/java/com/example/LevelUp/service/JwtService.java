package com.example.LevelUp.service;

import com.example.LevelUp.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtService {

    private final Key key = Keys.hmacShaKeyFor("9f3c2a87d1e64b55c6f4ab90e1d73c5f2d89b7aa34df56e0a1bf92c47e68d12f".getBytes());

    public String generateToken(Usuario user){

        Map<String, Object> claims = new HashMap<>();

        claims.put("correo", user.getCorreo());
        claims.put("id", user.getId());

        claims.put("rol", user.getRol());
        return Jwts.builder()
                .setClaims(claims) // 👈 ¡FALTABA ESTA LÍNEA!
                .setSubject(user.getCorreo())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                    .signWith(key)
                    .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean isvalid(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
