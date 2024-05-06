package com.CoderXAmod.Electronic.controller;

import com.CoderXAmod.Electronic.Services.CartService;
import com.CoderXAmod.Electronic.dtos.AddItemToCartRequest;
import com.CoderXAmod.Electronic.dtos.ApiResponseMessage;
import com.CoderXAmod.Electronic.dtos.CartDto;
import com.CoderXAmod.Electronic.reposoteries.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
@Autowired
private CartService cartService;
@PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest addItemToCartRequest)
    {
        CartDto cartDto = cartService.addItemToCard(userId, addItemToCartRequest);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{itemsId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromTheCart(@PathVariable String userId,@PathVariable int itemsId)
    {
        cartService.removeItemFromCart(userId,itemsId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().success(true).status(HttpStatus.GONE).Message("Items Delete Sucessfully 👍👍").build();
      return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId)
    {
        cartService.clearCart(userId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().success(true).status(HttpStatus.GONE).Message("card cleared  Sucessfully 👍👍").build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId)
    {
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}
