package com.michael.shipping_system.service;

import com.michael.shipping_system.model.Location;
import com.michael.shipping_system.model.User;
import com.michael.shipping_system.repo.LocationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LocationService {

    private final LocationRepo locationRepo;

    public Location addLocation(Location location){
        return locationRepo.save(location);
    }

    public List<Location> getAllLocation(){
        return locationRepo.findAll();
    }
}
