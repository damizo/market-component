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
            Product cigarettes = productRepository.save(cigarettes());
            Product apple = productRepository.save(apple());
            Product ice = productRepository.save(ice());
            Product whiskey = productRepository.save(whiskey());

            MultiItemsDiscount multiItemsDiscount = new MultiItemsDiscount();
            multiItemsDiscount.addProduct(cola);
            multiItemsDiscount.addProduct(flakes);
            multiItemsDiscount.addProduct(snickers);
            multiItemsDiscount.addProduct(cigarettes);
            multiItemsDiscount.addProduct(apple);
            multiItemsDiscount.addProduct(ice);
            multiItemsDiscount.addProduct(whiskey);

            BoughtTogetherDiscount boughtTogetherDiscount = new BoughtTogetherDiscount();
            BoughtTogetherDiscountPair wineAndRafaello = pair(wine, rafaello);
            BoughtTogetherDiscountPair whiskeyAndIce = pair(whiskey, ice);
            boughtTogetherDiscount.addPair(wineAndRafaello);
            boughtTogetherDiscount.addPair(whiskeyAndIce);

            productRepository.save(snickers);
            productRepository.save(cola);
            productRepository.save(flakes);
            productRepository.save(wine);
            productRepository.save(rafaello);
            productRepository.save(cigarettes);
            productRepository.save(apple);
            productRepository.save(ice);
            productRepository.save(whiskey);
        };
    }


}
