package com.michael.shipping_system.requestValid;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestOrderCreate {

    private Integer sendUserId;

    private Integer receiptUserId;

    @NotNull(message = "Pick up location can't be null")
    private Integer pickupLocationId;

    @NotNull(message = "Delivery location can't be null")
    private Integer deliveryLocationId;
}
