package com.michael.shipping_system.controller;

import com.michael.shipping_system.model.Order;
import com.michael.shipping_system.requestValid.RequestOrderCreate;
import com.michael.shipping_system.service.OrderService;
import com.michael.shipping_system.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/v1/staff")
@RequiredArgsConstructor
@Slf4j
public class StaffController {

    @Autowired
    private final TrackingService trackingService;

    @Autowired
    private OrderService orderService;


    //ROLE_STAFF AND ROLE_ADMIN: create shipping order
    @PostMapping("/orders/shipping")
    @PreAuthorize("hasAnyRole('ROLE_STAFF','ROLE_ADMIN')")
    public ResponseEntity<?> shipping(@RequestBody @Valid RequestOrderCreate orderCreate) throws Exception{
        try{
            log.info("{}",orderCreate.getPickupLocationId());
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/orders/shipping").toUriString());
            return ResponseEntity.created(uri).body(orderService.createOrder(orderCreate));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e);
        }

    }

    //Staff ROLE: Update Order pick up state
    @PreAuthorize("hasAnyRole('ROLE_STAFF','ROLE_ADMIN')")
    @PutMapping("/pickup")
    public ResponseEntity<Order> pickedUp(@RequestBody Order order){
        Order target = trackingService.updatePickUp(order.getSearchId());

        if (target != null){
            return ResponseEntity.status(HttpStatus.OK).body(target);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Staff ROLE: Update Order processing state
    @PreAuthorize("hasAnyRole('ROLE_STAFF','ROLE_ADMIN')")
    @PutMapping("/processing")
    public ResponseEntity<Order> processing(@RequestBody Order order){

        Order target = trackingService.updateProcessing(order.getSearchId());
        if (target != null){
            return ResponseEntity.status(HttpStatus.OK).body(target);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Staff ROLE: Update Order delivery state
    @PreAuthorize("hasAnyRole('ROLE_STAFF','ROLE_ADMIN')")
    @PutMapping("/delivered")
    public ResponseEntity<Order> deliveried( @RequestBody Order order){
        Order target = trackingService.updateDeliveried(order.getSearchId());
        if (target != null){
            return ResponseEntity.status(HttpStatus.OK).body(target);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
