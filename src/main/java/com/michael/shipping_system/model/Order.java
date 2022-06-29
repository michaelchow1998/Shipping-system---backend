package com.michael.shipping_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "send_user_id")
    private Long sendUserId;

    @Column(name = "receipt_user_id")
    private Long receiptUserId;


    @Column(name = "pickup_location_id")
    private Integer pickupLocationId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pickup_location_name",referencedColumnName = "id")
    private Location pickupLocation;

    @Column(name = "delivery_location_id")
    private Integer deliveryLocationId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_location_name",referencedColumnName = "id")
    private Location deliveryLocation;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "expect_date")
    private Date expectDate;

    @Column(name = "actual_arrival_date")
    private Date actualArrivalDate;

    @Column(name = "search_id")
    private String searchId;

    private Boolean finished;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="tracking_details_id", referencedColumnName="order_id")
    private TrackingDetails tarckingDetails;
}
