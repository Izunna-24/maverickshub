package com.maverickstube.maverickshub.security.filters;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maverickstube.maverickshub.dataTransferObjects.requests.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.hibernate.Hibernate.map;

@AllArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {

   private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            //1.retrieve authentication credential
            InputStream requestBodyStream = request.getInputStream();
            LoginRequest loginRequest = objectMapper
                    .readValue(requestBodyStream, LoginRequest.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            //3. Create an authentication object that is not yet authenticated
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            return authenticationResult;
        } catch (IOException exception) {
            throw new  BadCredentialsException(exception.getMessage());
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
       String token =  JWT.create()
                       .withIssuer("mavericks_hub")
                       .withArrayClaim("roles",getClaimsFrom(authResult.getAuthorities()))
                       .withExpiresAt(Instant.now().plusSeconds(24 * 60 * 60))
                       .sign(Algorithm.HMAC512("secret"));
       Map<String,String> res = new HashMap<>();
       res.put("access_token", token);
       response.getOutputStream().write(objectMapper.writeValueAsBytes(res));
       response.flushBuffer();
       chain.doFilter(request,response);

    }

    private static String[] getClaimsFrom(Collection<? extends GrantedAuthority> authorities){
       return authorities.stream()
        .map((grantedAuthority) -> grantedAuthority.getAuthority())
               .toArray(String[]::new);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

    }
}
