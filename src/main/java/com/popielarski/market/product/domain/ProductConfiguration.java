package com.popielarski.market.product.domain;

import com.popielarski.market.discount.domain.boughttogether.BoughtTogetherDiscount;
import com.popielarski.market.discount.domain.boughttogether.BoughtTogetherDiscountPair;
import com.popielarski.market.discount.domain.multiitems.MultiItemsDiscount;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfiguration extends ProductDataContainer {

    @Bean
    public ProductFacade productFacade(ProductRepository productRepository) {
        return new ProductFacade(productRepository, new ProductFactory(), new ProductMapper());
    }

    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository) {
        return (args) -> {
            Product cola = productRepository.save(cola());
            Product snickers = productRepository.save(snickers());
            Product rafaello = productRepository.save(rafaello());
            Product flakes = productRepository.save(flakes());
            Product wine = productRepository.save(wine());

            MultiItemsDiscount multiItemsDiscount = new MultiItemsDiscount();
            multiItemsDiscount.addProduct(cola);
            multiItemsDiscount.addProduct(flakes);
            multiItemsDiscount.addProduct(snickers);

            BoughtTogetherDiscount boughtTogetherDiscount = new BoughtTogetherDiscount();
            BoughtTogetherDiscountPair pair = pair(wine, rafaello);
            boughtTogetherDiscount.addPair(pair);

            productRepository.save(snickers);
            productRepository.save(cola);
            productRepository.save(flakes);
            productRepository.save(wine);
            productRepository.save(rafaello);
        };
    }
}
