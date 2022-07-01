package com.michael.shipping_system.repo;

import com.michael.shipping_system.model.TrackingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRepo extends JpaRepository<TrackingDetails, Integer> {

    TrackingDetails findBySearchId(String searchId);

    void deleteBySearchId(String searchId);

}
