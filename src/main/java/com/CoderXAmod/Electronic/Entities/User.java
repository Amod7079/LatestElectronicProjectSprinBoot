package com.CoderXAmod.Electronic.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@Table(name = "Users") // Corrected table name
public class User {
    @Id
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_Password", length = 10)
    private String password;

    private String gender;

    @Column(length = 1000)
    private String about;

    @Column(name = "user_Image")
    private String userImage; // Changed variable name to follow Java naming conventions
    @OneToMany(fetch =FetchType.LAZY,mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Order> orders=new ArrayList<>();

}
