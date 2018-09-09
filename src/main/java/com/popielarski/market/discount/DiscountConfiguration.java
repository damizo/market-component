package com.popielarski.market.discount;

import com.popielarski.market.cart.CartRepository;
import com.popielarski.market.discount.DiscountFacade;
import com.popielarski.market.discount.DiscountMapper;
import com.popielarski.market.discount.DiscountStrategyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscountConfiguration {

    @Bean
    DiscountFacade discountFacade(CartRepository cartRepository){
        return new DiscountFacade(new DiscountStrategyFactory(), new DiscountMapper(), cartRepository);
    }
}
