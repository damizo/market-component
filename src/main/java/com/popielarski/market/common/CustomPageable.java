package com.popielarski.market.common;

import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.PageRequest;

public class CustomPageable extends PageRequest {
    public CustomPageable(int page, int size) {
        super(page, size);
    }

    public CustomPageable(){
        super(0, 10);
    }
}
