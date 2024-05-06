package com.CoderXAmod.Electronic.controller;

import com.CoderXAmod.Electronic.Services.FileService;
import com.CoderXAmod.Electronic.Services.ProductService;
import com.CoderXAmod.Electronic.dtos.ApiResponseMessage;
import com.CoderXAmod.Electronic.dtos.ImageResponse;
import com.CoderXAmod.Electronic.dtos.PageableResponce;
import com.CoderXAmod.Electronic.dtos.ProductDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private FileService fileService;
    @Value("${product.image.path}")
    private String imagePath;
    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        ProductDto createdProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId) {
        {
            productService.deleteProduct(productId);
            ApiResponseMessage message = ApiResponseMessage.builder().Message("product Is Deleted Sucessfully !! 👍").success(true).status(HttpStatus.OK).build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    // get Single
    @GetMapping("{productId}")
    public ResponseEntity<ProductDto> getSngleProductByID(@PathVariable String productId) {
        ProductDto singleProduct = productService.getProductById(productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.IM_USED);
    }

    // GETAll
    @GetMapping
    public ResponseEntity<PageableResponce<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "SortDir", defaultValue = "asc", required = false) String SortDir
    ) {
        PageableResponce<ProductDto> allProduct = productService.getAllProduct(pageNumber, pageSize, sortBy, SortDir);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);

    }

    //GetAllLive 🚗

    @GetMapping("/live")
    public ResponseEntity<PageableResponce<ProductDto>> getAllProductWhichIsLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "SortDir", defaultValue = "asc", required = false) String SortDir
    ) {
        PageableResponce<ProductDto> allLiveProduct = productService.getAllLive(pageNumber, pageSize, sortBy, SortDir);
        return new ResponseEntity<>(allLiveProduct, HttpStatus.OK);

    }

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponce<ProductDto>> searchProductByTitle(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "SortDir", defaultValue = "asc", required = false) String SortDir
    ) {
        PageableResponce<ProductDto> allLiveProduct = productService.searchByTitle(query, pageNumber, pageSize, sortBy, SortDir);
        return new ResponseEntity<>(allLiveProduct, HttpStatus.OK);

    }

    // upload Image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage") MultipartFile image

    ) throws IOException {
        String fileName = fileService.UploadFile(image, imagePath);
        ProductDto productById = productService.getProductById(productId);
        productById.setProductImage(fileName);
        ProductDto updatedProduct = productService.updateProduct(productById, productId);
        ImageResponse response = ImageResponse.builder().ImageName(updatedProduct.getProductImage()).Message("Product Image Is SucessFully Uploaded 👍").status(HttpStatus.CREATED).success(true).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // serve image
    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productById = productService.getProductById(productId);
        InputStream resourse = fileService.getResourse(imagePath, productById.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resourse, response.getOutputStream());
    }

}
