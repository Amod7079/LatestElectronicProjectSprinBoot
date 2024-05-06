package com.CoderXAmod.Electronic.reposoteries;

import com.CoderXAmod.Electronic.Entities.Categories;
import com.CoderXAmod.Electronic.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    Page<Product> findByTitleContaining(Pageable pageable,String subTitle);
   Page<Product> findByLiveTrue(Pageable pageable);

        Page<Product> findByCategories(Categories categories, Pageable pageable);


}
