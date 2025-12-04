package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String SECRET_KEY = "jXcm30PljgTrqeMnbuE";

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String email, String role, Integer id){

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("userId", id);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email) //El usuario principal
                .setIssuedAt(new Date(System.currentTimeMillis()))  //Hora de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) //Caduca en 10 horas
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();


    }



}
