package com.CoderXAmod.Electronic.Services.Impl;

import com.CoderXAmod.Electronic.Entities.Categories;
import com.CoderXAmod.Electronic.Entities.Product;
import com.CoderXAmod.Electronic.Exception.ResourseNotFoundException;
import com.CoderXAmod.Electronic.Services.ProductService;
import com.CoderXAmod.Electronic.dtos.PageableResponce;
import com.CoderXAmod.Electronic.dtos.ProductDto;
import com.CoderXAmod.Electronic.helper.Helper;
import com.CoderXAmod.Electronic.reposoteries.CategoryRepository;
import com.CoderXAmod.Electronic.reposoteries.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Value("${product.image.path}")
    private String imagePath;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        // Map ProductDto to Product entity
        Product product = mapper.map(productDto, Product.class);

        // Generate and set product Id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);

        //LocalDateTime myDateObj = LocalDateTime.now();
        // DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        // Set added date
        product.setAddedDate(new Date());

        // Save the Product entity
        Product savedProduct = productRepository.save(product);

        // Map saved Product entity back to ProductDto and return
        return mapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not Found With Given Product Id😒"));
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setLive(product.isLive());
        product.setStock(productDto.isStock());
        product.setQuantity(productDto.getQuantity());
        product.setProductImage(productDto.getProductImage());
        Product updatedProduct = productRepository.save(product);
        return mapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not Found With Given Product Id😒"));
        String fullPath = imagePath + product.getProductImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);

        } catch (Exception e) {
            e.printStackTrace();
        }
        productRepository.delete(product);
    }

    @Override
    public ProductDto getProductById(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not Found With Given Product Id😒"));
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponce<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    //  @GetMapping("/live")
    @Override
    public PageableResponce<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponce<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = productRepository.findByTitleContaining(pageable, subTitle);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        // Retrieve the category entity
        Categories categories = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourseNotFoundException("Category Not Found !!"));

        // Map the ProductDto to Product entity
        Product product = mapper.map(productDto, Product.class);

        // Generate and set product Id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);

        // Set added date
        product.setAddedDate(new Date());

        // Set category
        product.setCategories(categories);

        // Save the Product entity
        Product savedProduct = productRepository.save(product);

        // Map saved Product entity back to ProductDto and return
        return mapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourseNotFoundException("Product Of Given Id Not Found"));
        Categories categories = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category Not Found With given product Id "));
        product.setCategories(categories);
        //Product SavedProduct = productRepository.save(product);
        return mapper.map(productRepository.save(product), ProductDto.class);
    }

    @Override//means categoryid k sare products laani hai

    public PageableResponce<ProductDto> getAllCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Categories categories = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourseNotFoundException("Category Not Found With given product Id "));
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByCategories(categories, pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }


}
