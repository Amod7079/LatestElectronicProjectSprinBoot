package com.CoderXAmod.Electronic.Services.Impl;

import com.CoderXAmod.Electronic.Entities.Categories;
import com.CoderXAmod.Electronic.Exception.ResourseNotFoundException;
import com.CoderXAmod.Electronic.Services.CategorryService;
import com.CoderXAmod.Electronic.dtos.CategoriesDto;
import com.CoderXAmod.Electronic.dtos.PageableResponce;
import com.CoderXAmod.Electronic.helper.Helper;
import com.CoderXAmod.Electronic.reposoteries.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategorryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoriesDto create(CategoriesDto categoriesDto) {
        //creating CategoryId:Randomly
        String categoryId = UUID.randomUUID().toString();
        categoriesDto.setCategoryId(categoryId);
        Categories category = mapper.map(categoriesDto, Categories.class);
        Categories savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CategoriesDto.class);
    }

    @Override
    public CategoriesDto update(CategoriesDto categoriesDto, String categoryId) {
        Categories categories = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category Not Found with Id !!😒"));
        categories.setTitle(categoriesDto.getTitle());
        categories.setDescription(categoriesDto.getDescription());
        categories.setCategoryId(categoriesDto.getCategoryId());
        categories.setCoverImage(categoriesDto.getCoverImage());
        Categories updatedCategory = categoryRepository.save(categories);
        CategoriesDto updatedCategoryDto = mapper.map(categories, CategoriesDto.class);
        return updatedCategoryDto;
    }

    @Override
    public void delete(String categoryId) {
        Categories category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category Not Found with Id !!😒"));
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponce<CategoriesDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Categories> page = categoryRepository.findAll(pageable);
        PageableResponce<CategoriesDto> pageableResponse = Helper.getPageableResponse(page, CategoriesDto.class);
        return pageableResponse;
    }

    @Override
    public CategoriesDto getSingleCategory(String categoryId) {
        Categories singleCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category Not Found With Given id 😒"));
        CategoriesDto categoryDto = mapper.map(singleCategory, CategoriesDto.class);
        // System.out.println("Hey Your Prodcuct is saved sucessfully in database 👍👍");
        return categoryDto;
    }
}
