package com.ecommerce.order.services;

import com.ecommerce.order.clients.ProductServiceClient;
import com.ecommerce.order.clients.UserServiceClient;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.ProductResponse;
import com.ecommerce.order.dto.UserResponse;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepo;
    private final ProductServiceClient productService;
    private final UserServiceClient userService;
    int attempt = 0;

//    @CircuitBreaker(name = "cartService", fallbackMethod = "addToCartFallback")
    @Retry(name = "cartServiceRetry", fallbackMethod = "addToCartFallback")
    public boolean addToCart(String userId, CartItemRequest request) {
        System.out.println("ATTEMPT COUNT: " + ++attempt);
        // Look for product and user
        ProductResponse productOpt = productService.getProductDetails(request.getProductId());
        if (productOpt == null || productOpt.getStockQuantity() < request.getQuantity()) {
            return false;
        }

        UserResponse userOpt = userService.getUserDetails(userId);
        if (userOpt == null) {
            return false;
        }

        CartItem existingCartItem = cartItemRepo.findByUserIdAndProductId(userId, request.getProductId());
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepo.save(existingCartItem);
        } else {
            // Create a new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepo.save(cartItem);
        }
        return true;
    }

    public boolean addToCartFallback(String userId, CartItemRequest request, Exception exception) {
        exception.printStackTrace();
        return false;
    }

    public boolean removeFromCart(String userId, Long productId) {
        CartItem cartItem = cartItemRepo.findByUserIdAndProductId(userId, productId);

        if (cartItem != null) {
            cartItemRepo.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepo.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepo.deleteByUserId(userId);
    }
}
