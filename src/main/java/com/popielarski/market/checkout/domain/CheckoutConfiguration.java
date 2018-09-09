package com.popielarski.market.checkout.domain;

import com.popielarski.market.cart.CartMapper;
import com.popielarski.market.cart.CartRepository;
import com.popielarski.market.item.ItemFactory;
import com.popielarski.market.item.ItemMapper;
import com.popielarski.market.product.domain.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CheckoutConfiguration {

    @Bean
    public CheckoutFacade checkoutFacade(ProductRepository productRepository, CartRepository cartRepository) {
        ItemMapper itemMapper = new ItemMapper();
        CartMapper cartMapper = new CartMapper(itemMapper);
        return new CheckoutFacade(new CheckoutCacheRepository(), new ItemFactory(),
                new CheckoutMapper(), cartMapper, productRepository, cartRepository);
    }
}
