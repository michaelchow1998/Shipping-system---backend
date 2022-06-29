package com.michael.shipping_system.controller;

import com.michael.shipping_system.model.Order;
import com.michael.shipping_system.service.OrderService;
import com.michael.shipping_system.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/staff")
@RequiredArgsConstructor
@Slf4j
public class StaffController {

    private final TrackingService trackingService;

    @PostMapping("/pickup")
    public ResponseEntity<Order> pickedUp(@RequestBody String searchId){
        Order order = trackingService.updatePickUp(searchId);

        if (order != null){
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/processing")
    public ResponseEntity<Order> processing(@RequestBody String searchId){
        Order order = trackingService.updateProcessing(searchId);

        if (order != null){
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/deliveried")
    public ResponseEntity<Order> deliveried( @RequestBody String searchId){
        Order order = trackingService.updateDeliveried(searchId);
        if (order != null){
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
