package com.michael.shipping_system.service;

import com.michael.shipping_system.model.Order;
import com.michael.shipping_system.Enum.State;
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


    public void deleteDetails(String searchId){
        trackingRepo.deleteBySearchId(searchId);
    }

    public TrackingDetails findDetails(String searchId){

        return trackingRepo.findBySearchId(searchId);
    }

    public Order updatePickUp(String searchId){
        Order order = orderService.searchBySearchId(searchId);
        if(order!=null){
            TrackingDetails trackingDetails = trackingRepo.findBySearchId(searchId);
            trackingDetails.setCurrentState(State.PICKUPED);
            trackingDetails.setPickedUp(true);
            trackingDetails.setProcessing(false);
            trackingDetails.setDelivered(false);
            trackingRepo.save(trackingDetails);
            order.setFinished(false);
            order.setActualArrivalTime(null);
            orderService.saveOrder(order);
            return order;
        }else{
            log.error("TrackingService: can't find order by {}", searchId );
            return null;
        }

    }

    public Order updateProcessing(String searchId){
        Order order = orderService.searchBySearchId(searchId);
        log.info("update order search id: {}",order.getSearchId());
        if(order!=null){
            TrackingDetails trackingDetails = trackingRepo.findBySearchId(searchId);
            trackingDetails.setCurrentState(State.PROCESSING);
            trackingDetails.setPickedUp(false);
            trackingDetails.setProcessing(true);
            trackingDetails.setDelivered(false);
            trackingRepo.save(trackingDetails);
            order.setFinished(false);
            order.setActualArrivalTime(null);
            orderService.saveOrder(order);
            return order;
        }else{
            log.error("TrackingService: can't find order by {}", searchId );
            return null;
        }

    }

    public Order updateDeliveried(String searchId){
        Order order = orderService.searchBySearchId(searchId);
        if(order!=null){
            TrackingDetails trackingDetails = trackingRepo.findBySearchId(searchId);
            trackingDetails.setCurrentLocationId(order.getDeliveryLocationId());
            trackingDetails.setCurrentState(State.DELIVERED);
            trackingDetails.setPickedUp(false);
            trackingDetails.setProcessing(false);
            trackingDetails.setDelivered(true);
            trackingRepo.save(trackingDetails);
            Date now = new Date();
            order.setActualArrivalTime(now);
            order.setFinished(true);
            orderService.saveOrder(order);
            return order;
        }else{
            log.error("TrackingService: can't find order by {}", searchId );
            return null;
        }

    }
}
