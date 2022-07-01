package com.michael.shipping_system.service;

import com.michael.shipping_system.model.User;
import com.michael.shipping_system.repo.UserRepo;
import com.michael.shipping_system.requestValid.RequestChangePw;
import com.michael.shipping_system.requestValid.RequestUserCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null){
            log.error("User not found in the db");
            throw new UsernameNotFoundException("User not found in the db");
        }else{
            log.info("User found in the db: {}",username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorities);
    }

    public User changePw(RequestChangePw requestChangePw){
        User targetUser = userRepo.findByUsername(requestChangePw.getUsername());
        String requestUsername = requestChangePw.getUsername().trim();
        String requestEmail = requestChangePw.getEmail().trim();
        String keyQuestionAns = requestChangePw.getKeyQuestionAns().trim();

        if(requestUsername.equals(targetUser.getUsername()) &&
                requestEmail.equals(targetUser.getEmail()) &&
                keyQuestionAns.equals(targetUser.getKeyQuestionAns())
        ){
            targetUser.setPassword(passwordEncoder.encode(requestChangePw.getNewPassword()));
            userRepo.save(targetUser);
            return targetUser;

        }else {
            throw new RuntimeException("Request information is not correct");
        }
    }

    public User register(RequestUserCreate user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User regUser = new User();
        regUser.setUsername(user.getUsername());
        regUser.setPassword(user.getPassword());
        regUser.setFirstName(user.getFirstName());
        regUser.setLastName(user.getLastName());
        regUser.setEmail(user.getEmail());
        regUser.setPhone(user.getPhone());
        regUser.setSex(user.getSex());
        regUser.setRole("ROLE_USER");
        regUser.setKeyQuestionAns(user.getKeyQuestionAns());
        Date now = new Date();
        regUser.setCreatedAt(now);
        log.info("Saving new user {} {} to the db",user.getFirstName(),user.getLastName());
        return userRepo.save(regUser);
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
