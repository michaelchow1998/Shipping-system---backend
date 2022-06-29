package com.michael.shipping_system.service;

import com.michael.shipping_system.model.Sex;
import com.michael.shipping_system.model.User;
import com.michael.shipping_system.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private final UserRepo userRepo;

    public User register(User user) {
        Date now = new Date();
        user.setCreatedAt(now);


        log.info("Saving new user {} {} to the db",user.getFirstName(),user.getLastName());
        return userRepo.save(user);
    }

    public User getUser(String username) {
        log.info("Fetching user {}",username);
        return userRepo.findByUsername(username);
    }

    public List<User> getAllUser(){
        return userRepo.findAll();
    }

    public void deleteUser(String username){
        userRepo.deleteByUsername(username);
    }


}
