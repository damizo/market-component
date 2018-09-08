package com.popielarski.market.item.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
class ItemConfiguration {

    @Bean
    ItemFacade itemFacade(ItemRepository itemRepository){
        return new ItemFacade(itemRepository);
    }

}
