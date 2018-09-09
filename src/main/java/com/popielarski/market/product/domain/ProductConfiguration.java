package com.popielarski.market.product.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfiguration {

    @Bean
    public ProductFacade productFacade(ProductRepository productRepository) {
        return new ProductFacade(productRepository, new ProductFactory(), new ProductMapper());
    }
}
