package com.CoderXAmod.Electronic.Services;

import com.CoderXAmod.Electronic.dtos.AddItemToCartRequest;
import com.CoderXAmod.Electronic.dtos.CartDto;

public interface CartService {
    CartDto addItemToCard(String userId, AddItemToCartRequest request);


    // remove item from cart
    void removeItemFromCart(String userId,int cartItem);
    // remove all the item from cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
