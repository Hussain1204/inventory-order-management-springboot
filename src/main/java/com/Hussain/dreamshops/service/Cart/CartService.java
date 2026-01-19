package com.Hussain.dreamshops.service.Cart;

import com.Hussain.dreamshops.exceptions.ResourceNotFoundException;
import com.Hussain.dreamshops.model.Cart;
import com.Hussain.dreamshops.model.CartItem;
import com.Hussain.dreamshops.model.Product;
import com.Hussain.dreamshops.repository.CartItemRepository;
import com.Hussain.dreamshops.repository.CartRepository;
import com.Hussain.dreamshops.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public Cart addItemToCart(Long userId, Long productId, Integer quantity) {

        Cart cart = cartRepository.findById(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setTotalAmount(BigDecimal.ZERO);
            return cartRepository.save(newCart);
        });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElse(new CartItem(cart, product, 0));

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setTotalPrice(
                product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
        );

        cartItemRepository.save(cartItem);

        cart.setTotalAmount(calculateTotal(cart.getId()));
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Override
    public void clearCart(Long cartId) {
        cartItemRepository.deleteAllByCartId(cartId);
        Cart cart = getCart(cartId);
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        return calculateTotal(cartId);
    }

    @Override
    public Long initializeNewCart() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.ZERO);
        return cartRepository.save(cart).getId();
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    private BigDecimal calculateTotal(Long cartId) {
        return cartItemRepository.findByCartId(cartId)
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }
}

