package com.CoderXAmod.Electronic.dtos;

import com.CoderXAmod.Electronic.Entities.CartItem;
import com.CoderXAmod.Electronic.Entities.User;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private String cartId;
    private Date createdAt;
    private UserDto user;
    private List<CartItemDto> items =new ArrayList<>();
}
