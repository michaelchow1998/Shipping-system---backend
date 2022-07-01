package com.michael.shipping_system.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michael.shipping_system.model.User;
import com.michael.shipping_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class TokenController {

    private final UserService userService;

    //JWT Token refresh
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //get AUTHORIZATION in header
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        //check authorization Header format
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("shippingSigningKey".getBytes());       //create sign key
                JWTVerifier verifier = JWT.require(algorithm).build();                          //create verifier
                DecodedJWT decodedJWT= verifier.verify(refresh_token);                          //decode JWT

                // Get User and create roles
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                List<String> roles = new ArrayList<>();
                roles.add(user.getRole());

                //create access token
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))     //One day
                        .withIssuer(request.getRequestURI())
                        .withClaim("roles",roles)
                        .sign(algorithm);

                //send response with the tokens
                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }catch (Exception e){

                log.error("Error logging in: {}", e.getMessage());

                //403 response error message
                response.setHeader("error",e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String,String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }

        }else {
            throw new RuntimeException("Refresh token is missing");
        }
    }


}
