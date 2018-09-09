package com.popielarski.market.common.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public class PageableHelper {

    public static <T> Page<T> of(List<T> list, Pageable pageable){
        return new PageImpl<T>(list, pageable ,list.size());
    }
}
