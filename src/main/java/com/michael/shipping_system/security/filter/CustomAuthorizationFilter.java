package com.michael.shipping_system.security.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/v1/login") || request.getServletPath().equals("/api/v1/token/refresh")
        || request.getServletPath().equals("/api/v1/guest/register") || request.getServletPath().equals("/api/v1/guest/changepw")
        || request.getServletPath().equals("/api/v1/guest/orders/**") || request.getServletPath().equals("/api/v1/guest/locations")
        ){
            //let the request and response keep going
            filterChain.doFilter(request,response);
        }else {

            //get the AUTHORIZATION Header
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                try {

                    //Create token
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("shippingSigningKey".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT= verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                    //add roles to authorities
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role ->{
                        authorities.add(new SimpleGrantedAuthority(role));
                    });

                    //create UsernamePasswordAuthenticationToken with username and authorities
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request,response);
                }catch (Exception e){

                    //send back error message and set in header
                    log.error("Error logging in: {}", e.getMessage());
                    response.setHeader("error",e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String,String> error = new HashMap<>();
                    error.put("error_message", e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);

                }

            }else {
                //let the request and response keep going
                filterChain.doFilter(request,response);
            }
        }


    }
}
