package com.popielarski.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({
        "com.popielarski.market.product.domain",
        "com.popielarski.market.cart",
        "com.popielarski.market.checkout.domain",
        "com.popielarski.market.discount.domain",
        "com.popielarski.market.discount.domain.boughttogether",
        "com.popielarski.market.item"
})
public class MarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketApplication.class, args);
    }
}
