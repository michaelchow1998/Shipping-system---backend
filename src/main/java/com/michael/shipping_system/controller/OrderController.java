package com.michael.shipping_system.controller;

import com.michael.shipping_system.model.Order;
import com.michael.shipping_system.model.RequestOrderCreate;
import com.michael.shipping_system.service.OrderService;
import com.michael.shipping_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("")
    public ResponseEntity<List<Order>> searchAllOrdersRelated(@RequestBody String username){
        List<Order> orders = orderService.searchAllOrdersRelated(username);

        if (orders != null){
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/shipping")
    public ResponseEntity<Order> shipping(@RequestBody RequestOrderCreate orderCreate){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/orders/shipping").toUriString());
        return ResponseEntity.created(uri).body(orderService.createOrder(orderCreate));
    }

    @PostMapping("/sent")
    public ResponseEntity<List<Order>> searchSendOrders(@RequestBody String username){
        List<Order> sendOrders = orderService.searchSendOrders(username);

        if (sendOrders != null){
            return ResponseEntity.status(HttpStatus.OK).body(sendOrders);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/receipted")
    public ResponseEntity<List<Order>> searchReceiptOrders(@RequestBody String username){
        List<Order> receiptOrders = orderService.searchReceiptOrders(username);

        if (receiptOrders != null){
            return ResponseEntity.status(HttpStatus.OK).body(receiptOrders);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/finished")
    public ResponseEntity<List<Order>> searchAllFinishedOrdersRelated(@RequestBody String username){
        List<Order> orders = orderService.searchAllFinishedOrdersRelated(username);

        if (orders != null){
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/unfinished")
    public ResponseEntity<List<Order>> searchAllUnfinishedOrdersRelated(@RequestBody String username){
        List<Order> orders = orderService.searchAllUnfinishedOrdersRelated(username);

        if (orders != null){
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
