package com.CoderXAmod.Electronic.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {



    @Id
    private String cartId;


    private Date createdAt;
    @OneToOne
    private User user;
// ab cart k andr kitne items hai
    // mapping cartItems se
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    private List<CartItem> items =new ArrayList<>();

}
