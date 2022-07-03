package com.michael.shipping_system.controller;

import com.michael.shipping_system.model.Location;
import com.michael.shipping_system.model.Order;
import com.michael.shipping_system.model.User;
import com.michael.shipping_system.requestValid.RequestChangePw;
import com.michael.shipping_system.requestValid.RequestUserCreate;
import com.michael.shipping_system.service.LocationService;
import com.michael.shipping_system.service.OrderService;
import com.michael.shipping_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/guest")
@RequiredArgsConstructor
@Slf4j
@Validated
public class GuestController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private LocationService locationService;

    //ADD: User register
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RequestUserCreate user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/register").toUriString());
        return ResponseEntity.created(uri).body(userService.register(user));
    }

    //PUT: UPDATE User password
    @PutMapping("/changepw")
    public ResponseEntity<User> register(@RequestBody @Valid RequestChangePw request){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/register").toUriString());
        return ResponseEntity.created(uri).body(userService.changePw(request));
    }


    //GUEST: GET order by searchId
    @GetMapping("/orders/{searchId}")
    public ResponseEntity<Order> searchOrder(@PathVariable String searchId){

        Order order = orderService.searchBySearchId(searchId);
        if (order.getId() != null){
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    //GUEST: READ all Location information
    @GetMapping("/locations")
    public ResponseEntity <List<Location>> getAllLocation(){
        List<Location> locations = locationService.getAllLocation();
        if (locations != null){
            return ResponseEntity.status(HttpStatus.OK).body(locations);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/orders/{searchID}/exists")
    public ResponseEntity <Map<String,Object>> checkOrdersExist(@PathVariable String searchID){
        HashMap<String, Object> map = new HashMap<>();
        Boolean exists = orderService.existsBySearchId(searchID);
        map.put("searchID", searchID);
        map.put("exists", exists);
        return ResponseEntity.status(HttpStatus.OK).body(map);

    }

}
