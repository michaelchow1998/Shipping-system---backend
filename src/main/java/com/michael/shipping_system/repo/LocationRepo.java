package com.michael.shipping_system.repo;

import com.michael.shipping_system.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<Location, Long> {
    Location findLocationById(Long Id);
}
