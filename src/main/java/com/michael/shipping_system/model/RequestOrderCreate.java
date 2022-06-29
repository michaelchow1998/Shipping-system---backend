package com.michael.shipping_system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestOrderCreate {

    private Long sendUserId;

    private Long receiptUserId;

    private Integer pickupLocationId;

    private Integer deliveryLocationId;
}
