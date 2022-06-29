package com.michael.shipping_system.repo;

import com.michael.shipping_system.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;


public interface OrderRepo extends PagingAndSortingRepository<Order,Long> {
    Order findBySearchId(String searchId);
    List<Order> findBySendUserIdOrderByCreatedDate (Long sendUserId);
    List<Order> findByReceiptUserIdOrderByCreatedDate ( Long receiptUserId);

    List<Order> findBySendUserIdAndReceiptUserIdOrderByCreatedDate (Long sendUserId, Long receiptUserId);
}
