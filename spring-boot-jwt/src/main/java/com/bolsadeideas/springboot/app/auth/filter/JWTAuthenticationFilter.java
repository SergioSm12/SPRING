package com.bolsadeideas.springboot.app.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }

        if (username != null && password != null) {
            logger.info("Username desde request parameter (form-data): " + username);
            logger.info("Pasword desde request parameter (form-data): " + password);

        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);


        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //Obtener el nombre
        String username = ((User) authResult.getPrincipal()).getUsername();

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

        //Encriptado
        //SecretKey secretKey = new SecretKeySpec("Alguna.Clave.Secreta.123456".getBytes(), SignatureAlgorithm.HS256.getJcaName());
        //Revisar que la clave sea de 256 bits
        SecretKey secretKey = Keys.hmacShaKeyFor("#n&<T2`g6S5Ph-fTJM3AHNT}+o~sjkue".getBytes());
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .compact();

        response.addHeader("Authorization", "Bearer" + token);

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("token", token);
        body.put("user", (User) authResult.getPrincipal());
        body.put("mensaje", String.format("Hola %s, has iniciado sesión con exito", username));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");

    }
}
