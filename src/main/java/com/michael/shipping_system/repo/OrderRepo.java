package com.michael.shipping_system.repo;

import com.michael.shipping_system.model.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface OrderRepo extends PagingAndSortingRepository<Order,Integer> {


    boolean existsBySearchId(String searchId);
    Order findBySearchId(String searchId);

    void deleteBySearchId(String searchId );
    List<Order> findBySendUserIdOrderByCreatedDate (Integer sendUserId);
    List<Order> findByReceiptUserIdOrderByCreatedDate ( Integer receiptUserId);

    List<Order> findBySendUserIdAndReceiptUserIdOrderByCreatedDate (Integer sendUserId, Integer receiptUserId);
}
