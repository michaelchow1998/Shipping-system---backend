package com.michael.shipping_system.controller;

import com.michael.shipping_system.model.User;
import com.michael.shipping_system.requestValid.RequestChangePw;
import com.michael.shipping_system.requestValid.RequestUserCreate;
import com.michael.shipping_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RequestUserCreate user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/register").toUriString());
        return ResponseEntity.created(uri).body(userService.register(user));
    }

    @PostMapping("/changepw")
    public ResponseEntity<User> register(@RequestBody @Valid RequestChangePw request){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/register").toUriString());
        return ResponseEntity.created(uri).body(userService.changePw(request));
    }




}
