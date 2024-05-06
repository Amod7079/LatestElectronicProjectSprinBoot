package com.CoderXAmod.Electronic.Services.Impl;

import com.CoderXAmod.Electronic.Entities.Cart;
import com.CoderXAmod.Electronic.Entities.CartItem;
import com.CoderXAmod.Electronic.Entities.Product;
import com.CoderXAmod.Electronic.Entities.User;
import com.CoderXAmod.Electronic.Exception.ResourseNotFoundException;
import com.CoderXAmod.Electronic.Services.CartService;
import com.CoderXAmod.Electronic.Services.UserService;
import com.CoderXAmod.Electronic.dtos.AddItemToCartRequest;
import com.CoderXAmod.Electronic.dtos.CartDto;
import com.CoderXAmod.Electronic.reposoteries.CartItemRepository;
import com.CoderXAmod.Electronic.reposoteries.CartRepository;
import com.CoderXAmod.Electronic.reposoteries.ProductRepository;
import com.CoderXAmod.Electronic.reposoteries.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemRepository cardItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCard(String userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourseNotFoundException("product  not found in the database"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User Not Found with given id"));
        Cart cart = null;

        try {
            cart = cartRepository.findByUser(user).get();
        } catch (Exception ex) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        //perform cart operation
        List<CartItem> items = cart.getItems();
        // IF CART ITEMS IS ALREADY PRESENT ; THEN UPDATE KRNA HOGA USKO
       items = items.stream().map(item ->
        {
            if (item.getProduct().equals(productId)) {
                // iteam is already present in the cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());
     //   cart.setItems(updatedItems);

        // create items
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart updatedCard = cartRepository.save(cart);
        return mapper.map(updatedCard, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        CartItem cartItem1 = cardItemRepository.findById(cartItem).orElseThrow(() -> new ResourseNotFoundException("Item not dound with given cart iTem "));
        cardItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("card not found with given ids"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourseNotFoundException("card not found with given ids"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("card not found with given ids"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourseNotFoundException("card not found with given ids"));
        return  mapper.map(cart, CartDto.class);
    }
}
