package com.michael.shipping_system.requestValid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLocationsList {

    @JsonProperty("pickup_location")
    private Integer pickLocationId;

    @JsonProperty("delivery_location")
    private Integer deliveryLocationId;

}
