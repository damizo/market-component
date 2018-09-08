package com.popielarski.market.item;

import com.popielarski.market.item.domain.ItemFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemFacade itemFacade;

}
