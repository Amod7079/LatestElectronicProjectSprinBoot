package com.CoderXAmod.Electronic.reposoteries;

import com.CoderXAmod.Electronic.Entities.Cart;
import com.CoderXAmod.Electronic.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository  extends JpaRepository<Cart,String> {
   Optional< Cart> findByUser (User user);
}
