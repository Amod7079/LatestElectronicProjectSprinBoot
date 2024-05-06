package com.CoderXAmod.Electronic.reposoteries;

import com.CoderXAmod.Electronic.Entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categories,String> {
}
