package com.CoderXAmod.Electronic.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="categories")
public class Categories {
    @Id
    @Column(name="id")
    private  String categoryId;
    @Column(name ="category_title",length=60)
    private String title;
    @Column(name ="category_desc ",length = 500)
    public String description ;
    public String coverImage;
    @OneToMany(mappedBy = "categories",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> productList=new ArrayList<>();
}
