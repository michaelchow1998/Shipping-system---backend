package com.michael.shipping_system.requestValid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michael.shipping_system.Enum.LocationState;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RequestChangeLocation {


    @JsonProperty("state")
    private LocationState state;
}
