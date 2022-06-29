package com.michael.shipping_system.controller;

import com.michael.shipping_system.model.Location;
import com.michael.shipping_system.model.User;
import com.michael.shipping_system.service.LocationService;
import com.michael.shipping_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserService userService;

    private final LocationService locationService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.getAllUser();
        if (users != null){
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable String username){
        userService.deleteUser(username);
        log.info("{} deleted in db", username);
    }

    @GetMapping("/locations")
    public ResponseEntity <List<Location>> getAllLocation(){
        List<Location> locations = locationService.getAllLocation();
        if (locations != null){
            return ResponseEntity.status(HttpStatus.OK).body(locations);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/locations")
    public ResponseEntity <Location> addLocation(@RequestBody Location location) throws Exception{
        List<Location> locations = locationService.getAllLocation();
        boolean contain = false;
        for (Location l : locations){
            if(l.getName() == location.getName()){
                contain = true;
            }
        }
        if(contain){
           throw new IllegalAccessException("Location name already used");
        }else {
            location = locationService.addLocation(location);
            return ResponseEntity.status(HttpStatus.CREATED).body(location);
        }
    }

}
