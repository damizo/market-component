package com.popielarski.market.product.domain;

import com.google.common.collect.Sets;
import com.popielarski.market.discount.domain.boughttogether.BoughtTogetherDiscount;
import com.popielarski.market.discount.domain.boughttogether.BoughtTogetherDiscountRepository;
import com.popielarski.market.discount.domain.multiitems.MultiItemsDiscount;
import com.popielarski.market.discount.domain.multiitems.MultiItemsDiscountRepository;
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
    public CommandLineRunner loadData(ProductRepository productRepository,
                                      MultiItemsDiscountRepository multiItemsDiscountRepository,
                                      BoughtTogetherDiscountRepository boughtTogetherDiscountRepository) {
        return (args) -> {
            Product cola = productRepository.save(cola());
            Product snickers = productRepository.save(snickers());
            Product rafaello = productRepository.save(rafaello());
            Product flakes = productRepository.save(flakes());
            Product wine = productRepository.save(wine());

            BoughtTogetherDiscount boughtTogetherDiscount = boughtTogetherDiscountRepository.save(boughtTogetherDiscount(Sets
                    .newHashSet(pair(wine, rafaello))));

            MultiItemsDiscount multiItemsDiscount = multiItemsDiscountRepository.save(multiItemsDiscount(Sets
                    .newHashSet(cola, snickers, flakes)));

            wine.setBoughtTogetherDiscount(boughtTogetherDiscount);
            rafaello.setBoughtTogetherDiscount(boughtTogetherDiscount);

            flakes.setMultiItemsDiscount(multiItemsDiscount);
            snickers.setMultiItemsDiscount(multiItemsDiscount);
            cola.setMultiItemsDiscount(multiItemsDiscount);

            productRepository.save(flakes);
            productRepository.save(wine);
            productRepository.save(rafaello);
        };
    }
}
