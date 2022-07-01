package com.michael.shipping_system.controller;

import com.michael.shipping_system.model.*;
import com.michael.shipping_system.service.OrderService;
import com.michael.shipping_system.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private final TrackingService trackingService;

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<List<Order>> searchAllOrdersRelated(@RequestBody User user){

        List<Order> orders = orderService.searchAllOrdersRelated(user.getUsername());

        if (orders != null){
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/details")
    public ResponseEntity<TrackingDetails> searchOrderDetail(@RequestBody TrackingDetails details){
        log.info("getSearchId : {}", details.getSearchId());
        TrackingDetails targetDetail = trackingService.findDetails(details.getSearchId());
        if (targetDetail != null){
            return ResponseEntity.status(HttpStatus.OK).body(targetDetail);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }




    @PostMapping("/{searchId}")
    public ResponseEntity<Order> searchOrder(@RequestBody String searchId){
       Order order = orderService.searchBySearchId(searchId);
        if (order.getId() != null){
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping("/shipping")
    @PreAuthorize("hasAnyRole('ROLE_STAFF','ROLE_ADMIN')")
    public ResponseEntity<Order> shipping(@RequestBody RequestOrderCreate orderCreate){
        log.info("{}",orderCreate.getPickupLocationId());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/orders/shipping").toUriString());
        return ResponseEntity.created(uri).body(orderService.createOrder(orderCreate));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/sent")
    public ResponseEntity<List<Order>> searchSendOrders(@RequestBody User user){
        List<Order> sendOrders = orderService.searchSendOrders(user.getUsername());

        if (sendOrders != null){
            return ResponseEntity.status(HttpStatus.OK).body(sendOrders);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/receipted")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Order>> searchReceiptOrders(@RequestBody User user){
        List<Order> receiptOrders = orderService.searchReceiptOrders(user.getUsername());

        if (receiptOrders != null){
            return ResponseEntity.status(HttpStatus.OK).body(receiptOrders);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/finished")
    public ResponseEntity<List<Order>> searchAllFinishedOrdersRelated(@RequestBody User user){
        List<Order> orders = orderService.searchAllFinishedOrdersRelated(user.getUsername());

        if (orders != null){
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/unfinished")
    public ResponseEntity<List<Order>> searchAllUnfinishedOrdersRelated(@RequestBody  User user){
        List<Order> orders = orderService.searchAllUnfinishedOrdersRelated(user.getUsername());

        if (orders != null){
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
