package com.CoderXAmod.Electronic.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {
    @NotBlank(message = "Cart id is required !!")
    private String cartId;
    @NotBlank(message = "User id is required !!")
    private String userId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    @NotBlank(message = "Address id is required !!")
    private String billingAddress;
    @NotBlank(message = "Phone Number   is required !!")
    private String billingPhone;
    @NotBlank(message = "Billing Name   is required !!")
    private String billingName;


    // private User user;

}

