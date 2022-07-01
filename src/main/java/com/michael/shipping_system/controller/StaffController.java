package com.michael.shipping_system.controller;

import com.michael.shipping_system.model.Order;
import com.michael.shipping_system.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @PostMapping("/pickup")
    public ResponseEntity<Order> pickedUp(@RequestBody Order order){
        Order target = trackingService.updatePickUp(order.getSearchId());

        if (target != null){
            return ResponseEntity.status(HttpStatus.OK).body(target);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @PostMapping("/processing")
    public ResponseEntity<Order> processing(@RequestBody Order order){

        Order target = trackingService.updateProcessing(order.getSearchId());
        if (target != null){
            return ResponseEntity.status(HttpStatus.OK).body(target);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @PostMapping("/deliveried")
    public ResponseEntity<Order> deliveried( @RequestBody Order order){
        Order target = trackingService.updateDeliveried(order.getSearchId());
        if (target != null){
            return ResponseEntity.status(HttpStatus.OK).body(target);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
