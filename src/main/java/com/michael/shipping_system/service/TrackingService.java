package com.michael.shipping_system.service;

import com.michael.shipping_system.model.Order;
import com.michael.shipping_system.model.State;
import com.michael.shipping_system.model.TrackingDetails;
import com.michael.shipping_system.repo.TrackingRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TrackingService {

    private final TrackingRepo trackingRepo;

    private final OrderService orderService;



    public Order updatePickUp(String searchId){
        Order order = orderService.searchBySearchId(searchId);
        if(order!=null){
            Long orderId = order.getId();
            TrackingDetails trackingDetails = trackingRepo.findByOrderId(orderId);
            trackingDetails.setCurrentState(State.PICKUPED);
            trackingDetails.setPickedUp(true);
            trackingDetails.setProcessing(false);
            trackingDetails.setDeliveried(false);
            trackingRepo.save(trackingDetails);
            return order;
        }else{
            log.error("TrackingService: can't find order by {}", searchId );
            return null;
        }

    }

    public Order updateProcessing(String searchId){
        Order order = orderService.searchBySearchId(searchId);
        if(order!=null){
            Long orderId = order.getId();
            TrackingDetails trackingDetails = trackingRepo.findByOrderId(orderId);
            trackingDetails.setCurrentState(State.PROCESSING);
            trackingDetails.setPickedUp(false);
            trackingDetails.setProcessing(true);
            trackingDetails.setDeliveried(false);
            trackingRepo.save(trackingDetails);
            return order;
        }else{
            log.error("TrackingService: can't find order by {}", searchId );
            return null;
        }

    }

    public Order updateDeliveried(String searchId){
        Order order = orderService.searchBySearchId(searchId);
        if(order!=null){
            Long orderId = order.getId();
            TrackingDetails trackingDetails = trackingRepo.findByOrderId(orderId);
            trackingDetails.setCurrentLocationId(order.getDeliveryLocationId());
            trackingDetails.setCurrentState(State.DELIVERIED);
            trackingDetails.setPickedUp(false);
            trackingDetails.setProcessing(false);
            trackingDetails.setDeliveried(true);
            trackingRepo.save(trackingDetails);
            Date now = new Date();
            order.setActualArrivalDate(now);
            order.setFinished(true);
            orderService.saveOrder(order);
            return order;
        }else{
            log.error("TrackingService: can't find order by {}", searchId );
            return null;
        }

    }
}
