package com.michael.shipping_system.service;

import com.michael.shipping_system.model.*;
import com.michael.shipping_system.repo.OrderRepo;
import com.michael.shipping_system.repo.TrackingRepo;
import com.michael.shipping_system.repo.UserRepo;
import com.michael.shipping_system.requestValid.RequestOrderCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void deleteOrder(String searchId){
        orderRepo.deleteBySearchId(searchId);
    }

    public Order createOrder(RequestOrderCreate orderCreate){

        String uniqueID = UUID.randomUUID().toString().substring(0,7);

        Date now = new Date();
        Date sevenDays = new Date(now.getTime()+ (1000 * 60 * 60 * 24 * 7));
//        log.info("SendUserId: {}", orderCreate.getSendUserId());
//        log.info("ReceiptUserId: {}", orderCreate.getReceiptUserId());
//        log.info("PickupLocationId: {}", orderCreate.getPickupLocationId());
//        log.info("DeliveryLocationId: {}", orderCreate.getDeliveryLocationId());
        //user init
        Order order = new Order();
        order.setSearchId(uniqueID);
        order.setSendUserId(orderCreate.getSendUserId());
        order.setReceiptUserId(orderCreate.getReceiptUserId());
        order.setPickupLocationId(orderCreate.getPickupLocationId());
        order.setDeliveryLocationId(orderCreate.getDeliveryLocationId());
        order.setCreatedDate(now);
        order.setExpectDate(sevenDays);
        order.setActualArrivalTime(null);
        order.setFinished(false);

        //tracking init
        Order targetOrder = orderRepo.findBySearchId(uniqueID);
        TrackingDetails trackingDetails = new TrackingDetails();
        trackingDetails.setSearchId(uniqueID);
        trackingDetails.setCurrentLocationId(orderCreate.getPickupLocationId());
        trackingDetails.setCurrentState(State.PICKUPED);
        trackingDetails.setPickedUp(true);
        trackingRepo.save(trackingDetails);

        TrackingDetails detail = trackingRepo.findBySearchId(uniqueID);
        order.setDetails(detail);
        orderRepo.save(order);


        return orderRepo.findBySearchId(uniqueID);
    }
    public List<Order> getAllOrders(){
        List<Order> orders = (List<Order>) orderRepo.findAll();
        return orders;
    }

    public Order searchBySearchId(String searchId){
       Order order = orderRepo.findBySearchId(searchId);
        return order;
    }

    public List<Order> searchSendOrders(String username){
        User target = userRepo.findByUsername(username);
        Integer sendUserId = target.getId();
        List<Order> orders = orderRepo.findBySendUserIdOrderByCreatedDate(sendUserId);
        return orders;
    }

    public List<Order> searchReceiptOrders(String username){
        User user = userRepo.findByUsername(username);
        Integer receiptUserId = user.getId();
        List<Order> orders = orderRepo.findByReceiptUserIdOrderByCreatedDate(receiptUserId);
        return orders;
    }

    public List<Order> searchAllOrdersRelated(String username ){
        User user = userRepo.findByUsername(username);
        Integer UserId = user.getId();
        List<Order> orders = orderRepo.findByReceiptUserIdOrderByCreatedDate(UserId);
        List<Order> sentOrders = orderRepo.findBySendUserIdOrderByCreatedDate(UserId);
        orders.addAll(sentOrders);
        return orders;
    }

    public List<Order> searchAllFinishedOrdersRelated(String username ){
        User user = userRepo.findByUsername(username);
        Integer UserId = user.getId();
        List<Order> orders = orderRepo.findByReceiptUserIdOrderByCreatedDate(UserId);
        List<Order> sentOrders = orderRepo.findBySendUserIdOrderByCreatedDate(UserId);
        orders.addAll(sentOrders);
        orders = orders.stream().filter(order->order.getFinished() == true).collect(Collectors.toList());
        return orders;
    }

    public List<Order> searchAllUnfinishedOrdersRelated(String username ){
        User user = userRepo.findByUsername(username);
        Integer UserId = user.getId();
        List<Order> orders = orderRepo.findByReceiptUserIdOrderByCreatedDate(UserId);
        List<Order> sentOrders = orderRepo.findBySendUserIdOrderByCreatedDate(UserId);
        orders.addAll(sentOrders);
        orders = orders.stream().filter(order->order.getFinished() == false).collect(Collectors.toList());
        return orders;
    }




}