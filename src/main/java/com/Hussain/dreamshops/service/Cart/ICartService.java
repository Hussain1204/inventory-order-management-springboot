package com.Hussain.dreamshops.service.Cart;

import com.Hussain.dreamshops.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart addItemToCart(Long userId, Long productId, Integer quantity);

    Cart getCart(Long cartId);

    void clearCart(Long cartId);

    BigDecimal getTotalPrice(Long cartId);

    Long initializeNewCart();

    Cart getCartByUserId(Long userId);
}
