package com.CoderXAmod.Electronic.Services;

import com.CoderXAmod.Electronic.Entities.Order;
import com.CoderXAmod.Electronic.dtos.CreateOrderRequest;
import com.CoderXAmod.Electronic.dtos.OrderDto;
import com.CoderXAmod.Electronic.dtos.PageableResponce;

import java.util.List;

public interface OrderService {

    // create Order
 OrderDto   createOrder(CreateOrderRequest orderDto);


 void removeOrder(String orderId);

 // get orders of user
    List<OrderDto> getOrderOfUser(String userId);
    //get order
    PageableResponce<OrderDto> getOrder(int pageNumber,int pageSize,String sortBy,String sortDir);

}
