package com.CoderXAmod.Electronic.controller;

import com.CoderXAmod.Electronic.Services.OrderService;
import com.CoderXAmod.Electronic.dtos.ApiResponseMessage;
import com.CoderXAmod.Electronic.dtos.CreateOrderRequest;
import com.CoderXAmod.Electronic.dtos.OrderDto;
import com.CoderXAmod.Electronic.dtos.PageableResponce;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionUsageException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
        ApiResponseMessage orderIsRemoveSucessfully = ApiResponseMessage.builder().status(HttpStatus.OK).Message("Order is Remove sucessfully").success(true).build();

        return new ResponseEntity<>(orderIsRemoveSucessfully, HttpStatus.OK);
    }


    // get order of the user
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderOfUser(@PathVariable String userId) {

        List<OrderDto> orderOfUser = orderService.getOrderOfUser(userId);
        return new ResponseEntity<>(orderOfUser, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<PageableResponce<OrderDto>> getOrders(@PathVariable String userId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
    @RequestParam(value = "SortDir", defaultValue = "asc", required = false) String SortDir) {
        PageableResponce<OrderDto> order = orderService.getOrder(pageNumber, pageSize, sortBy, SortDir);
        return new ResponseEntity<>(order, HttpStatus.OK);


    }
}