package com.CoderXAmod.Electronic.dtos;

import com.CoderXAmod.Electronic.Entities.Order;
import com.CoderXAmod.Electronic.Entities.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderItemDto {
    private int orderItemId;
    private int quantity;
    private int totalPrice;
    private ProductDto product;
   // private Order order;
}
