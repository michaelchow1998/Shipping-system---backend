package com.michael.shipping_system.service;

import com.michael.shipping_system.model.Location;
import com.michael.shipping_system.repo.LocationRepo;
import com.michael.shipping_system.requestValid.RequestChangeLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String,String> getLocationName(List<Integer> locationList){
        HashMap<String,String> names = new HashMap();
        String pickupLocationName = locationRepo.findLocationById(locationList.get(0)).getName();
        String delivered  = locationRepo.findLocationById(locationList.get(1)).getName();
        names.put("pickUpName", pickupLocationName);
        names.put("deliveryName", delivered);
        return names;
    }

    public Location updateLocationState(Integer locationId, RequestChangeLocation req){
        Location targetLocation = locationRepo.findLocationById(locationId);
        targetLocation.setState(req.getState());
        return locationRepo.save(targetLocation);
    }

    public void deleteLocation(Location location){ locationRepo.delete(location); }

    public Location getLocationById(Integer id){return  locationRepo.findLocationById(id);}
}
