package com.bolsadeideas.springboot.app.auth.filter;

import com.bolsadeideas.springboot.app.auth.service.IJWTService;
import com.bolsadeideas.springboot.app.auth.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private IJWTService jwtService;
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, IJWTService jwtService) {
        super(authenticationManager);
        this.jwtService=jwtService;
    }

    //validacion de roles
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //Traemos los headers
        String header = request.getHeader(JWTService.HEADER_STRING);
        if (!requiresAuthentication(header)) {
            chain.doFilter(request, response);
            return;
        }


        //si el token es valido autorizamos
        UsernamePasswordAuthenticationToken authentication = null;
        if (jwtService.validate(header)) {

            authentication = new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null, jwtService.getRoles(header));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    protected boolean requiresAuthentication(String header) {
        if (header == null || !header.startsWith(JWTService.TOKEN_PREFIX)) {

            return false;
        }
        return true;
    }
}
