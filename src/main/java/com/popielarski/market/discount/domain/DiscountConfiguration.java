package com.popielarski.market.discount.domain;

import com.popielarski.market.cart.CartRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscountConfiguration {

    @Bean
    DiscountFacade discountFacade(CartRepository cartRepository){
        return new DiscountFacade(new DiscountStrategyFactory(), new DiscountMapper(), cartRepository);
    }
}
