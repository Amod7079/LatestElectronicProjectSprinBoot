package com.CoderXAmod.Electronic.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.assign.TypeCasting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="orders")
public class Order {
    @Id
private String orderId;
//pending , dispatched ,delivered
private String orderStatus;
private String paymentStatus;
private int orderAmount;
@Column(length = 1000)
private String billingAddress;
private String billingPhone;
private String billingName;
private Date orderedDate;
private Date deliverDate;
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "user_id")
private User user;
@OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
private List<OrderItem> orderItem=new ArrayList<>();
}
