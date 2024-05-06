package com.CoderXAmod.Electronic.Services;
import org.springframework.data.domain.Pageable;
import com.CoderXAmod.Electronic.dtos.PageableResponce;
import com.CoderXAmod.Electronic.dtos.ProductDto;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto,String productId);
    void deleteProduct(String productId);
    ProductDto getProductById(String productId);
    PageableResponce<ProductDto> getAllProduct(int pageNumber , int pageSize , String sortBy,String sortDir);
  PageableResponce<ProductDto> getAllLive(int pageNumber , int pageSize , String sortBy,String sortDir);
    // search product
   PageableResponce<ProductDto> searchByTitle(String subTitle,int pageNumber , int pageSize , String sortBy,String sortDir);

   // create Product With Category
    ProductDto createWithCategory(ProductDto productDto,String categoryId);
    // update category of product
    ProductDto updateCategory(String productId,String categoryId);
    PageableResponce<ProductDto> getAllCategory(String categoryId,int pageNumber , int pageSize , String sortBy,String sortDir);



}
