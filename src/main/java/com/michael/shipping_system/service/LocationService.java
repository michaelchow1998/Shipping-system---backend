package com.michael.shipping_system.service;

import com.michael.shipping_system.model.Location;
import com.michael.shipping_system.repo.LocationRepo;
import com.michael.shipping_system.requestValid.RequestChangeLocation;
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

    public Location updateLocationState(Integer locationId, RequestChangeLocation req){
        Location targetLocation = locationRepo.findLocationById(locationId);
        targetLocation.setState(req.getState());
        return locationRepo.save(targetLocation);
    }

    public void deleteLocation(Location location){ locationRepo.delete(location); }

    public Location getLocationById(Integer id){return  locationRepo.findLocationById(id);}
}
