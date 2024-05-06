package com.CoderXAmod.Electronic.controller;

import com.CoderXAmod.Electronic.Services.CategorryService;
import com.CoderXAmod.Electronic.Services.ProductService;
import com.CoderXAmod.Electronic.dtos.ApiResponseMessage;
import com.CoderXAmod.Electronic.dtos.CategoriesDto;
import com.CoderXAmod.Electronic.dtos.PageableResponce;
import com.CoderXAmod.Electronic.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategorryService categorryService;

    @Autowired
    private ProductService productService;

    // CREATE Controller of categories
    @PostMapping
    public ResponseEntity<CategoriesDto> createCategory(@RequestBody CategoriesDto categoriesDto) {
        CategoriesDto categoriesDto1 = categorryService.create(categoriesDto);
        return new ResponseEntity<>(categoriesDto1, HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoriesDto> updateCategory(@RequestBody CategoriesDto categoriesDto, @PathVariable String categoryId) {
        CategoriesDto updatedCategory = categorryService.update(categoriesDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        categorryService.delete(categoryId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .Message("Category  Is Deleted Sucessfully !! 👍")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Get All
    @GetMapping
    public ResponseEntity<PageableResponce<CategoriesDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "0", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "SortDir", defaultValue = "asc", required = false) String SortDir
    ) {
        PageableResponce<CategoriesDto> pageableResponce = categorryService.getAll(pageNumber, pageSize, sortBy, SortDir);
        return new ResponseEntity<>(pageableResponce, HttpStatus.OK);
    }

    // Get Single
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoriesDto> getSingleCategory(@PathVariable String categoryId) {
        CategoriesDto singleCategory = categorryService.getSingleCategory(categoryId);
        return new ResponseEntity<>(singleCategory, HttpStatus.OK);
    }

    // Create Product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto) {

        ProductDto productWithCategory = productService.createWithCategory(productDto, categoryId);

        return new ResponseEntity<>(productWithCategory, HttpStatus.OK);
    }

    //update Category of product
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(
            @PathVariable String categoryId,
            @PathVariable String productId
    ) {
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    // get Products of category
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponce<ProductDto>> getProductsOfCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "0", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "SortDir", defaultValue = "asc", required = false) String SortDir
    ) {
        PageableResponce<ProductDto> allCategory = productService.getAllCategory(categoryId, pageNumber, pageSize, sortBy, SortDir);
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }
}
