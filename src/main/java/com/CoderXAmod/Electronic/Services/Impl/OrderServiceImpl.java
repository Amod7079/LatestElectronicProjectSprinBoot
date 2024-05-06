package com.CoderXAmod.Electronic.Services.Impl;

import com.CoderXAmod.Electronic.Entities.*;
import com.CoderXAmod.Electronic.Exception.BadApiRequest;
import com.CoderXAmod.Electronic.Exception.ResourseNotFoundException;
import com.CoderXAmod.Electronic.Services.OrderService;
import com.CoderXAmod.Electronic.dtos.CreateOrderRequest;
import com.CoderXAmod.Electronic.dtos.OrderDto;
import com.CoderXAmod.Electronic.dtos.PageableResponce;
import com.CoderXAmod.Electronic.helper.Helper;
import com.CoderXAmod.Electronic.reposoteries.CartRepository;
import com.CoderXAmod.Electronic.reposoteries.OrderRepository;
import com.CoderXAmod.Electronic.reposoteries.UserRepository;
import org.apache.catalina.valves.HealthCheckValve;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    CartRepository cartRepository;
    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();
        //fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User Not found With Given ID"));
       //FETCH CARD
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourseNotFoundException("card not found !!"));

        List<CartItem> cartitems = cart.getItems();
        if (cartitems.size()<=0)
            throw new BadApiRequest("Invalid Number Of Items In Cart!!");


        Order order = Order.builder().billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliverDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();
        AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
        List<OrderItem> orderItems = cartitems.stream().map(cartItem -> {
            OrderItem orderItem = OrderItem.builder().quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();
orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());
            return  orderItem;
        }).collect(Collectors.toList());
order.setOrderItem(orderItems);
order.setOrderAmount(orderAmount.get());
cart.getItems().clear();
cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);
        return mapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
       Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourseNotFoundException("Order Not found With Given ID"));
       orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getOrderOfUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User Not found With Given ID"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos= orders.stream().map(order -> mapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public PageableResponce<OrderDto> getOrder(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable   = PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page, OrderDto.class);
    }
}
