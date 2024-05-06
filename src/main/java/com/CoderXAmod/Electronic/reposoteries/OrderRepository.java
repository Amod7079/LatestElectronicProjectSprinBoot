package com.CoderXAmod.Electronic.reposoteries;

import com.CoderXAmod.Electronic.Entities.Order;
import com.CoderXAmod.Electronic.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findByUser(User user);
}
