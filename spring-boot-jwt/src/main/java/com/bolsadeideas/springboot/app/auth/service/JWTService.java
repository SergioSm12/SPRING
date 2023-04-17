package com.bolsadeideas.springboot.app.auth.service;

import com.bolsadeideas.springboot.app.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class JWTService implements IJWTService {

    public static final String SECRET= Base64Utils.encodeToString("#n&<T2`g6S5Ph-fTJM3AHNT}+o~sjkue".getBytes());
    public static final long EXPIRATION_DATE=3600000;
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";

    @Override
    public String create(Authentication auth) throws JsonProcessingException {
        //Obtener el nombre
        String username = ((User) auth.getPrincipal()).getUsername();

        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

        //Encriptado
        //SecretKey secretKey = new SecretKeySpec("Alguna.Clave.Secreta.123456".getBytes(), SignatureAlgorithm.HS256.getJcaName());
        //Revisar que la clave sea de 256 bits
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .compact();
        return token;
    }

    @Override
    public boolean validate(String token) {
        //Decodificar Token
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(resolve(token))
                .getBody();
        return claims;
    }

    @Override
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    //Metodo para traer los roles del cuerpo de la peticion
    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
        return authorities;
    }

    @Override
    public String resolve(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        }
        return null;
    }
}
