package com.popielarski.market;

import com.popielarski.market.repository.ItemInMemoryRepository;
import com.popielarski.market.item.domain.ItemFacade;
import com.popielarski.market.item.domain.ItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemConfiguration {

    @Bean
    ItemRepository itemRepository(){
        return new ItemInMemoryRepository();
    }

    @Bean
    ItemFacade itemFacade(ItemRepository itemRepository){
        return new ItemFacade(itemRepository);
    }

}
