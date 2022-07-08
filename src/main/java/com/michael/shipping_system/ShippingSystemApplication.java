package com.michael.shipping_system;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ShippingSystemApplication {
//    ShipSheep_v1.0
    public static void main(String[] args) {
        SpringApplication.run(ShippingSystemApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//            Date now = new Date();
//            userService.register(new User(null, "michael", "1234", "Michael", "Chow", "michael@gmail.com", "45444545", Sex.M, "ROLE_ADMIN", now));
//            userService.register(new User(null, "joe", "4321", "Joe", "Lee", "joe@gmail.com", "12234567", Sex.M, "ROLE_STAFF", now));
//            userService.register(new User(null, "Gary", "1122", "Gary", "Chan", "gary@gmail.com", "45757252", Sex.M, "ROLE_USER", now));
//            userService.register(new User(null, "Mary", "3344", "Mary", "Mak", "mak@gmail.com", "45757252", Sex.F, "ROLE_USER", now));
//        };
//    }





}
