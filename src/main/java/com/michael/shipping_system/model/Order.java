package com.michael.shipping_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "order_search_id", length = 255)
    private String searchId;

    @Column(name = "user_id" ,length = 254)
    private String userId;

    @Column(name = "send_user_id")
    private Integer sendUserId;

    @Column(name = "receipt_user_id")
    private Integer receiptUserId;

    @Column(name = "pickup_location_id")
    private Integer pickupLocationId;

    @Column(name = "delivery_location_id")
    private Integer deliveryLocationId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "expect_date")
    private Date expectDate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "actual_arrival_time")
    private Date actualArrivalTime;

    @Column(name = "finished")
    private Boolean finished;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id")
    private TrackingDetails details;

}