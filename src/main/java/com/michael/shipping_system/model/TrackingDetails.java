package com.michael.shipping_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tracking_details")
@Getter
@Setter
public class TrackingDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "tracking_search_id")
    @JsonProperty("search_id")
    private String searchId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "details")
    private Order orders;

    @Column(name = "current_location_id", nullable = false)
    private Integer currentLocationId;

    @Column(name = "current_state", nullable = false, length = 10)
    private State currentState;

    @Column(name = "picked_up", nullable = false)
    private Boolean pickedUp = false;

    @Column(name = "processing", nullable = false)
    private Boolean processing = false;

    @Column(name = "delivered", nullable = false)
    private Boolean delivered = false;



}