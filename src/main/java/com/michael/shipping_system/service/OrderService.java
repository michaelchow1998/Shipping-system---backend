package com.michael.shipping_system.service;

import com.michael.shipping_system.model.*;
import com.michael.shipping_system.repo.OrderRepo;
import com.michael.shipping_system.repo.TrackingRepo;
import com.michael.shipping_system.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {


    private final OrderRepo orderRepo;

    private final UserRepo userRepo;

    private final TrackingRepo trackingRepo;

    public Order saveOrder(Order order){
       orderRepo.save(order);
        return order;
    }

    public Order createOrder(RequestOrderCreate orderCreate){
        String uniqueID = UUID.randomUUID().toString();
        Date now = new Date();
        Date sevenDays = new Date(now.getTime()+ (1000 * 60 * 60 * 24 * 7));

        //user init
        Order order = new Order();
        order.setSearchId(uniqueID);
        order.setSendUserId(orderCreate.getSendUserId());
        order.setReceiptUserId(orderCreate.getReceiptUserId());
        order.setPickupLocationId(orderCreate.getPickupLocationId());
        order.setDeliveryLocationId(orderCreate.getDeliveryLocationId());
        order.setCreatedDate(now);
        order.setExpectDate(sevenDays);
        order.setActualArrivalDate(null);
        order.setFinished(false);
        orderRepo.save(order);

        //tracking init
        Order targetOrder = orderRepo.findBySearchId(uniqueID);
        TrackingDetails trackingDetails = new TrackingDetails();
        trackingDetails.setOrderId(targetOrder.getId());
        trackingDetails.setCurrentLocationId(targetOrder.getPickupLocationId());
        trackingDetails.setCurrentState(State.PICKUPED);
        trackingDetails.setPickedUp(true);
        trackingRepo.save(trackingDetails);

        return orderRepo.findBySearchId(uniqueID);
    }

    public Order searchBySearchId(String searchId){
       Order order = orderRepo.findBySearchId(searchId);
        return order;
    }



    public List<Order> searchSendOrders(String username){
        User user = userRepo.findByUsername(username);
        Long sendUserId = user.getId();
        List<Order> orders = orderRepo.findBySendUserIdOrderByCreatedDate(sendUserId);
        return orders;
    }

    public List<Order> searchReceiptOrders(String username){
        User user = userRepo.findByUsername(username);
        Long receiptUserId = user.getId();
        List<Order> orders = orderRepo.findByReceiptUserIdOrderByCreatedDate(receiptUserId);
        return orders;
    }

    public List<Order> searchAllOrdersRelated(String username ){
        User user = userRepo.findByUsername(username);
        Long UserId = user.getId();
        List<Order> orders = orderRepo.findBySendUserIdAndReceiptUserIdOrderByCreatedDate(UserId,UserId);
        return orders;
    }

    public List<Order> searchAllFinishedOrdersRelated(String username ){
        User user = userRepo.findByUsername(username);
        Long UserId = user.getId();
        List<Order> orders = orderRepo.findBySendUserIdAndReceiptUserIdOrderByCreatedDate(UserId,UserId);
        orders = orders.stream().filter(order->order.getFinished() == true).collect(Collectors.toList());
        return orders;
    }

    public List<Order> searchAllUnfinishedOrdersRelated(String username ){
        User user = userRepo.findByUsername(username);
        Long UserId = user.getId();
        List<Order> orders = orderRepo.findBySendUserIdAndReceiptUserIdOrderByCreatedDate(UserId,UserId);
        orders = orders.stream().filter(order->order.getFinished() == false).collect(Collectors.toList());
        return orders;
    }




}