package com.michael.shipping_system.controller;

import com.michael.shipping_system.model.User;
import com.michael.shipping_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/register").toUriString());
        return ResponseEntity.created(uri).body(userService.register(user));
    }

    @GetMapping("{username}")
    public ResponseEntity<User> getUser(@PathVariable String username){
        User user = userService.getUser(username);
        log.info(user.getEmail());
        if (user.getId() != null){
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
