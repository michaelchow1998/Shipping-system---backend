package com.michael.shipping_system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestOrderCreate {

    private Integer sendUserId;

    private Integer receiptUserId;

    private Integer pickupLocationId;

    private Integer deliveryLocationId;
}
