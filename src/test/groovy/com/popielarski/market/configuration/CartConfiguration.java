package com.popielarski.market.configuration;

import com.popielarski.market.cart.CartRepository;
import com.popielarski.market.repository.CartInMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class CartConfiguration {

    @Bean
    CartRepository cartRepository(){
        return new CartInMemoryRepository();
    }

}
