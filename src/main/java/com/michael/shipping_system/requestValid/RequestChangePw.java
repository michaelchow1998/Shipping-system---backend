package com.michael.shipping_system.requestValid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RequestChangePw {

    @NotBlank
    private String username;

    private String email;

    @JsonProperty("new_password")
    @NotBlank
    private String newPassword;

    @JsonProperty("question_ans")
    @NotBlank
    private String keyQuestionAns;

}
