package com.CoderXAmod.Electronic.Entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="cart_items")
public class CartItem { // generally te batata hai ki kis item ko kis cart me kitne price pe ya total price me add kr rhe ho
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int cartItemId;
@OneToOne
@JoinColumn(name="product_id")
private Product product;
private int quantity;
private int totalPrice;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="card_id")
private Cart cart;

}
