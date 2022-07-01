package com.michael.shipping_system.controller;

import com.michael.shipping_system.model.Order;
import com.michael.shipping_system.model.User;
import com.michael.shipping_system.requestValid.RequestChangePw;
import com.michael.shipping_system.requestValid.RequestUserCreate;
import com.michael.shipping_system.service.OrderService;
import com.michael.shipping_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/guest/")
@RequiredArgsConstructor
@Slf4j
@Validated
public class GuestController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    //ADD: User register
    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody @Valid RequestUserCreate user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/register").toUriString());
        return ResponseEntity.created(uri).body(userService.register(user));
    }

    //PUT: UPDATE User password
    @PutMapping("changepw")
    public ResponseEntity<User> register(@RequestBody @Valid RequestChangePw request){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/register").toUriString());
        return ResponseEntity.created(uri).body(userService.changePw(request));
    }


    //ROLE_STAFF AND ROLE_ADMIN: GET order by searchId
    @PostMapping("orders/{searchId}")
    public ResponseEntity<Order> searchOrder(@RequestBody String searchId){
        Order order = orderService.searchBySearchId(searchId);
        if (order.getId() != null){
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

}
