package com.ecommerce.product.services;

import com.ecommerce.product.models.Product;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;


    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        mapFromProductRequest(product, productRequest);
        Product savedProduct = productRepo.save(product);
        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setCategory(product.getCategory());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setActive(product.getActive());
        return productResponse;
    }

    private void mapFromProductRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepo.findById(id)
                .map(existingProduct -> {
                    mapFromProductRequest(existingProduct, productRequest);
                    Product updatedProduct = productRepo.save(existingProduct);
                    return mapToProductResponse(updatedProduct);
                });
    }

    public List<ProductResponse> getAllProducts() {
        return productRepo.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        return productRepo.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepo.save(product);
                    return true;
                })
                .orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepo.searchProduct(keyword)
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> getProductById(Long id) {
        return productRepo.findByIdAndActiveTrue(id)
                .map(this::mapToProductResponse);
    }
}
