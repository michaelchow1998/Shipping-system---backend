package com.michael.shipping_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tarcking_details")
public class TrackingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "curent_location_id")
    private Integer currentLocationId;

    @Column(name = "current_state")
    private State currentState;

    @Column(name = "pickuped")
    private boolean pickedUp;

    private boolean processing;

    private boolean deliveried;
}
