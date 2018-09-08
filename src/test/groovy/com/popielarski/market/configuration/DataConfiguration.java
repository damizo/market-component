package com.popielarski.market.configuration;

import com.google.common.collect.Sets;
import com.popielarski.market.discount.boughttogether.BoughtTogetherDiscount;
import com.popielarski.market.discount.boughttogether.BoughtTogetherDiscountRepository;
import com.popielarski.market.discount.multiitems.MultiItemsDiscountRepository;
import com.popielarski.market.item.domain.MultiItemsDiscount;
import com.popielarski.market.product.Product;
import com.popielarski.market.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfiguration extends DataContainer {

    //TODO: adding item through facade

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

            BoughtTogetherDiscount boughtTogetherDiscount = boughtTogetherDiscountRepository.save(boughtTogetherDiscount(Sets.newHashSet(pair(wine, rafaello))));
            MultiItemsDiscount multiItemsDiscount = multiItemsDiscountRepository.save(multiItemsDiscount(Sets.newHashSet(cola)));

            wine.setBoughtTogetherDiscount(boughtTogetherDiscount);
            rafaello.setBoughtTogetherDiscount(boughtTogetherDiscount);
            flakes.setMultiItemsDiscount(multiItemsDiscount);

            productRepository.save(flakes);
            productRepository.save(wine);
            productRepository.save(rafaello);
        };
    }

}
